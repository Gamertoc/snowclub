/**********************************************************************
 * Copyright (c) by Heiner Jostkleigrewe
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
 *  the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, 
 * see <http://www.gnu.org/licenses/>.
 * 
 * heiner@jverein.de
 * www.jverein.de
 **********************************************************************/

package eu.snoware.SnowClub.gui.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import de.willuhn.datasource.BeanUtil;
import de.willuhn.datasource.GenericObject;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBObject;
import de.willuhn.datasource.serialize.Writer;
import de.willuhn.datasource.serialize.XmlWriter;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.messaging.StatusBarMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.system.BackgroundTask;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.ProgressMonitor;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.rmi.Beitragsgruppe;
import eu.snoware.SnowClub.rmi.Buchungsart;
import eu.snoware.SnowClub.rmi.ZusatzbetragAbrechnungslauf;
import eu.snoware.SnowClub.server.AbrechnungslaufImpl;
import eu.snoware.SnowClub.server.AdresstypImpl;
import eu.snoware.SnowClub.server.AnfangsbestandImpl;
import eu.snoware.SnowClub.server.ArbeitseinsatzImpl;
import eu.snoware.SnowClub.server.BuchungDokumentImpl;
import eu.snoware.SnowClub.server.BuchungImpl;
import eu.snoware.SnowClub.server.BuchungsklasseImpl;
import eu.snoware.SnowClub.server.EigenschaftGruppeImpl;
import eu.snoware.SnowClub.server.EigenschaftImpl;
import eu.snoware.SnowClub.server.EigenschaftenImpl;
import eu.snoware.SnowClub.server.EinstellungImpl;
import eu.snoware.SnowClub.server.FelddefinitionImpl;
import eu.snoware.SnowClub.server.FormularImpl;
import eu.snoware.SnowClub.server.FormularfeldImpl;
import eu.snoware.SnowClub.server.JahresabschlussImpl;
import eu.snoware.SnowClub.server.KontoImpl;
import eu.snoware.SnowClub.server.KursteilnehmerImpl;
import eu.snoware.SnowClub.server.LastschriftImpl;
import eu.snoware.SnowClub.server.LehrgangImpl;
import eu.snoware.SnowClub.server.LehrgangsartImpl;
import eu.snoware.SnowClub.server.LesefeldImpl;
import eu.snoware.SnowClub.server.MailAnhangImpl;
import eu.snoware.SnowClub.server.MailEmpfaengerImpl;
import eu.snoware.SnowClub.server.MailImpl;
import eu.snoware.SnowClub.server.MailVorlageImpl;
import eu.snoware.SnowClub.server.MitgliedDokumentImpl;
import eu.snoware.SnowClub.server.MitgliedImpl;
import eu.snoware.SnowClub.server.MitgliedNextBGruppeImpl;
import eu.snoware.SnowClub.server.MitgliedfotoImpl;
import eu.snoware.SnowClub.server.MitgliedskontoImpl;
import eu.snoware.SnowClub.server.ProjektImpl;
import eu.snoware.SnowClub.server.QIFImportHeadImpl;
import eu.snoware.SnowClub.server.QIFImportPosImpl;
import eu.snoware.SnowClub.server.SpendenbescheinigungImpl;
import eu.snoware.SnowClub.server.WiedervorlageImpl;
import eu.snoware.SnowClub.server.ZusatzbetragImpl;
import eu.snoware.SnowClub.server.ZusatzfelderImpl;
import eu.snoware.SnowClub.util.JVDateFormatJJJJMMTT;

/**
 * Action zum Erstellen eines Komplett-Backups im XML-Format.
 */
public class BackupCreateAction implements Action
{
  // Die Versionstabelle wird nicht mit kopiert

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    FileDialog fd = new FileDialog(GUI.getShell(), SWT.SAVE);
    fd.setFilterPath(System.getProperty("user.home"));
    fd.setFileName("jverein-backup-"
        + new JVDateFormatJJJJMMTT().format(new Date()) + ".xml");
    fd.setFilterExtensions(new String[] { "*.xml" });
    fd.setText(
        "Bitte wählen Sie die Datei, in der das Backup gespeichert wird");
    String f = fd.open();
    if (f == null || f.length() == 0)
      return;

    final File file = new File(f);
    try
    {
      if (file.exists() && !Application.getCallback()
          .askUser("Datei existiert bereits. Überschreiben?"))
        return;
    }
    catch (ApplicationException ae)
    {
      throw ae;
    }
    catch (Exception e)
    {
      Logger.error("error while asking user", e);
      Application.getMessagingFactory().sendMessage(
          new StatusBarMessage("Fehler beim Erstellen der Backup-Datei",
              StatusBarMessage.TYPE_ERROR));
      return;
    }

    Application.getController().start(new BackgroundTask()
    {

      private boolean cancel = false;

      /**
       * @see de.willuhn.jameica.system.BackgroundTask#run(de.willuhn.util.ProgressMonitor)
       */
      @Override
      public void run(ProgressMonitor monitor) throws ApplicationException
      {
        Writer writer = null;
        try
        {
          Logger.info("creating xml backup to " + file.getAbsolutePath());

          writer = new XmlWriter(
              new BufferedOutputStream(new FileOutputStream(file)));

          monitor.setStatusText("Speichere Abrechnungslauf-Informationen");
          backup(AbrechnungslaufImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Adresstypen");
          backup(AdresstypImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Buchungsklassen");
          backup(BuchungsklasseImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Eigenschaftengruppen");
          backup(EigenschaftGruppeImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Einstellungen");
          backup(EinstellungImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Felddefinitionen");
          backup(FelddefinitionImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Formulare");
          backup(FormularImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Formularfelder");
          backup(FormularfeldImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Konten");
          backup(KontoImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Kursteilnehmer");
          backup(KursteilnehmerImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Lehrgangsarten");
          backup(LehrgangsartImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Lesefelder");
          backup(LesefeldImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Mails");
          backup(MailImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Mailanhänge");
          backup(MailAnhangImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Mailvorlagen");
          backup(MailVorlageImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText(
              "Speichere Informationen über zukünftige Beitragsgruppen");
          backup(MitgliedNextBGruppeImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Projekte");
          backup(ProjektImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Informationen über QIF-Header");
          backup(QIFImportHeadImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Informationen über QIF-Positionen");
          backup(QIFImportPosImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Anfangsbestände");
          backup(AnfangsbestandImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Buchungsarten");
          backup(Buchungsart.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Eingenschaften");
          backup(EigenschaftImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Beitragsgruppen");
          backup(Beitragsgruppe.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Mitglieder");
          backup(MitgliedImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Dokumente der Mitglieder");
          backup(MitgliedDokumentImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Fotos der Mitglieder");
          backup(MitgliedfotoImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Mitgliedskonten");
          backup(MitgliedskontoImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Spendenbescheinigungen");
          backup(SpendenbescheinigungImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Wiedervorlagen");
          backup(WiedervorlageImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Zusatzbeträge");
          backup(ZusatzbetragImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText(
              "Speichere Informatioen über Abrechnungsläufe von Zusatzbeträgen");
          backup(ZusatzbetragAbrechnungslauf.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Zusatzfelder");
          backup(ZusatzfelderImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Arbeitseinsätze");
          backup(ArbeitseinsatzImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Buchungen");
          backup(BuchungImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Dokumente zu Buchungen");
          backup(BuchungDokumentImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Jahresabschlüsse");
          backup(JahresabschlussImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Eigenschaften der Mitglieder");
          backup(EigenschaftenImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Lastschriften");
          backup(LastschriftImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Lehrgänge");
          backup(LehrgangImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          monitor.setStatusText("Speichere Mailempfänger");
          backup(MailEmpfaengerImpl.class, writer, monitor);
          monitor.addPercentComplete(2);

          // Die Versionstabelle wird nicht mit kopiert

          monitor.setStatus(ProgressMonitor.STATUS_DONE);
          monitor.setStatusText("Backup erstellt");
          monitor.setPercentComplete(100);
        }
        catch (Exception e)
        {
          throw new ApplicationException(e.getMessage());
        }
        finally
        {
          if (writer != null)
          {
            try
            {
              writer.close();
              Logger.info("backup created");
            }
            catch (Exception e)
            {
              /* useless */}
          }
        }
      }

      /**
       * @see de.willuhn.jameica.system.BackgroundTask#isInterrupted()
       */
      @Override
      public boolean isInterrupted()
      {
        return this.cancel;
      }

      /**
       * @see de.willuhn.jameica.system.BackgroundTask#interrupt()
       */
      @Override
      public void interrupt()
      {
        this.cancel = true;
      }

    });
  }

  private static void backup(Class<? extends DBObject> type, Writer writer,
      ProgressMonitor monitor) throws Exception
  {
    DBIterator<?> list = Einstellungen.getDBService().createList(type);
    list.setOrder("order by id");
    long count = 1;
    while (list.hasNext())
    {
      GenericObject o = null;
      try
      {
        o = list.next();
        writer.write(o);
        if (count++ % 200 == 0)
          monitor.addPercentComplete(1);
      }
      catch (Exception e)
      {
        Logger.error("error while writing object " + BeanUtil.toString(o)
            + " - skipping", e);
        monitor.log(String.format("  %s fehlerhaft (%s), überspringe",
            BeanUtil.toString(o), e.getMessage()));
      }
    }
  }
}
