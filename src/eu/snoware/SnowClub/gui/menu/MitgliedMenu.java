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
package eu.snoware.SnowClub.gui.menu;

import java.rmi.RemoteException;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.SimpleDialog;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.CheckedSingleContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.gui.util.SWTUtil;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.action.AdresseDetailAction;
import eu.snoware.SnowClub.gui.action.FreiesFormularAction;
import eu.snoware.SnowClub.gui.action.KontoauszugAction;
import eu.snoware.SnowClub.gui.action.MitgliedArbeitseinsatzZuordnungAction;
import eu.snoware.SnowClub.gui.action.MitgliedDeleteAction;
import eu.snoware.SnowClub.gui.action.MitgliedDuplizierenAction;
import eu.snoware.SnowClub.gui.action.MitgliedEigenschaftZuordnungAction;
import eu.snoware.SnowClub.gui.action.MitgliedInZwischenablageKopierenAction;
import eu.snoware.SnowClub.gui.action.MitgliedLastschriftAction;
import eu.snoware.SnowClub.gui.action.MitgliedMailSendenAction;
import eu.snoware.SnowClub.gui.action.MitgliedVCardDateiAction;
import eu.snoware.SnowClub.gui.action.MitgliedVCardQRCodeAction;
import eu.snoware.SnowClub.gui.action.MitgliedZusatzbetraegeZuordnungAction;
import eu.snoware.SnowClub.gui.action.PersonalbogenAction;
import eu.snoware.SnowClub.gui.action.SpendenbescheinigungAction;
import eu.snoware.SnowClub.gui.view.MitgliedDetailView;
import eu.snoware.SnowClub.keys.FormularArt;
import eu.snoware.SnowClub.rmi.Formular;
import eu.snoware.SnowClub.rmi.Mitglied;

/**
 * Kontext-Menu zu den Mitgliedern
 */
public class MitgliedMenu extends ContextMenu
{

  /**
   * Erzeugt ein Kontext-Menu für die Liste der Mitglieder.
   * 
   * @throws RemoteException
   */
  public MitgliedMenu(Action detailaction) throws RemoteException
  {
    addItem(new CheckedSingleContextMenuItem("bearbeiten", detailaction,
        "edit.png"));
    addItem(new CheckedSingleContextMenuItem("duplizieren",
        new MitgliedDuplizierenAction(), "copy.png"));
    addItem(new CheckedContextMenuItem("in Zwischenablage kopieren",
        new MitgliedInZwischenablageKopierenAction(), "copy.png"));
    if (detailaction instanceof AdresseDetailAction)
    {
      addItem(new CheckedContextMenuItem("zu Mitglied umwandeln", new Action()
      {

        @Override
        public void handleAction(Object context) throws ApplicationException
        {
          Mitglied m = (Mitglied) context;
          try
          {
            SimpleDialog sd = new SimpleDialog(SimpleDialog.POSITION_CENTER);
            sd.setText(
                "Bitte die für Mitglieder erforderlichen Daten nacherfassen.");
            sd.setSideImage(SWTUtil.getImage("dialog-warning-large.png"));
            sd.setSize(400, 150);
            sd.setTitle("Daten nacherfassen");
            try
            {
              sd.open();
            }
            catch (Exception e)
            {
              Logger.error("Fehler", e);
            }
            m.setAdresstyp(1);
            m.setEingabedatum();
            GUI.startView(MitgliedDetailView.class.getName(), m);
          }
          catch (RemoteException e)
          {
            throw new ApplicationException(e);
          }
        }
      }, "arrows-alt-h.png"));
    }
    addItem(new CheckedSingleContextMenuItem("löschen...",
        new MitgliedDeleteAction(), "trash-alt.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new CheckedContextMenuItem("Mail senden ...",
        new MitgliedMailSendenAction(), "envelope-open.png"));
    addItem(new CheckedContextMenuItem("vCard-Datei",
        new MitgliedVCardDateiAction(), "address-card.png"));
    addItem(new CheckedSingleContextMenuItem("vCard QR-Code",
        new MitgliedVCardQRCodeAction(), "qr-code.png"));
    addItem(new CheckedContextMenuItem("Eigenschaften",
        new MitgliedEigenschaftZuordnungAction(), "check-double.png"));
    if (Einstellungen.getEinstellung().getArbeitseinsatz())
    {
      addItem(new CheckedContextMenuItem("Arbeitseinsätze zuweisen",
          new MitgliedArbeitseinsatzZuordnungAction(), "screwdriver.png"));
    }
    addItem(new CheckedContextMenuItem("Zusatzbeträge zuweisen",
        new MitgliedZusatzbetraegeZuordnungAction(), "coins.png"));
    addItem(new CheckedContextMenuItem("Kontoauszug", new KontoauszugAction(),
        "rechnung.png"));
    addItem(new CheckedSingleContextMenuItem("Spendenbescheinigung",
        new SpendenbescheinigungAction(), "file-invoice.png"));
    addItem(new CheckedContextMenuItem("Personalbogen",
        new PersonalbogenAction(), "file-invoice.png"));
    addItem(new CheckedSingleContextMenuItem("Manuelle Lastschrift ...",
        new MitgliedLastschriftAction(), "file-invoice.png"));
    DBIterator<Formular> it = Einstellungen.getDBService()
        .createList(Formular.class);
    it.addFilter("art = ?",
        new Object[] { FormularArt.FREIESFORMULAR.getKey() });
    while (it.hasNext())
    {
      Formular f = (Formular) it.next();
      addItem(new CheckedContextMenuItem(f.getBezeichnung(),
          new FreiesFormularAction(f.getID()), "file-invoice.png"));
    }
  }
}
