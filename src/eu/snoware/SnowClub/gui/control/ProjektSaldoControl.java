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
package eu.snoware.SnowClub.gui.control;

import java.io.File;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.Part;
import de.willuhn.jameica.gui.input.DateInput;
import de.willuhn.jameica.gui.parts.Button;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.system.BackgroundTask;
import de.willuhn.jameica.system.Settings;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.ProgressMonitor;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.parts.ProjektSaldoList;
import eu.snoware.SnowClub.io.ProjektSaldoPDF;
import eu.snoware.SnowClub.io.ProjektSaldoZeile;
import eu.snoware.SnowClub.util.Dateiname;
import eu.snoware.SnowClub.util.SCDateFormatTTMMJJJJ;

public class ProjektSaldoControl extends AbstractControl
{

  private ProjektSaldoList saldoList;

  private DateInput datumvon;

  private DateInput datumbis;

  private Settings settings = null;

  public ProjektSaldoControl(AbstractView view)
  {
    super(view);
    settings = new Settings(this.getClass());
    settings.setStoreWhenRead(true);
  }

  public DateInput getDatumvon()
  {
    if (datumvon != null)
    {
      return datumvon;
    }
    Calendar cal = Calendar.getInstance();
    Date d = new Date();
    try
    {
      d = new SCDateFormatTTMMJJJJ()
          .parse(settings.getString("von", "01.01" + cal.get(Calendar.YEAR)));
    }
    catch (ParseException e)
    {
      //
    }
    datumvon = new DateInput(d, new SCDateFormatTTMMJJJJ());
    return datumvon;
  }

  public DateInput getDatumbis()
  {
    if (datumbis != null)
    {
      return datumbis;
    }
    Calendar cal = Calendar.getInstance();
    Date d = new Date();
    try
    {
      d = new SCDateFormatTTMMJJJJ()
          .parse(settings.getString("bis", "31.12." + cal.get(Calendar.YEAR)));
    }
    catch (ParseException e)
    {
      //
    }
    datumbis = new DateInput(d, new SCDateFormatTTMMJJJJ());
    return datumbis;
  }

  public Button getStartAuswertungButton()
  {
    Button b = new Button("PDF", new Action()
    {
      @Override
      public void handleAction(Object context) throws ApplicationException
      {
        starteAuswertung();
      }
    }, null, true, "file-pdf.png"); // "true" defines this button as the default
    // button
    return b;
  }

  public void handleStore()
  {
    //
  }

  public Part getSaldoList() throws ApplicationException
  {
    try
    {
      if (getDatumvon().getValue() != null)
      {
        settings.setAttribute("von",
            new SCDateFormatTTMMJJJJ().format((Date) getDatumvon().getValue()));
      }
      if (getDatumvon().getValue() != null)
      {
        settings.setAttribute("bis",
            new SCDateFormatTTMMJJJJ().format((Date) getDatumbis().getValue()));
      }

      if (saldoList == null)
      {
        saldoList = new ProjektSaldoList(null, (Date) datumvon.getValue(),
            (Date) datumbis.getValue());
      }
      else
      {
        settings.setAttribute("von",
            new SCDateFormatTTMMJJJJ().format((Date) getDatumvon().getValue()));

        saldoList.setDatumvon((Date) datumvon.getValue());
        saldoList.setDatumbis((Date) datumbis.getValue());
        ArrayList<ProjektSaldoZeile> zeile = saldoList.getInfo();
        saldoList.removeAll();
        for (ProjektSaldoZeile sz : zeile)
        {
          saldoList.addItem(sz);
        }
      }
    }
    catch (RemoteException e)
    {
      throw new ApplicationException(
          String.format("Fehler aufgetreten %s", e.getMessage()));
    }
    return saldoList.getSaldoList();
  }

  private void starteAuswertung() throws ApplicationException
  {
    try
    {
      ArrayList<ProjektSaldoZeile> zeile = saldoList.getInfo();

      FileDialog fd = new FileDialog(GUI.getShell(), SWT.SAVE);
      fd.setText("Ausgabedatei w?hlen.");
      //
      Settings settings = new Settings(this.getClass());
      //
      String path = settings.getString("lastdir",
          System.getProperty("user.home"));
      if (path != null && path.length() > 0)
      {
        fd.setFilterPath(path);
      }
      fd.setFileName(new Dateiname("projektsaldo", "",
          Einstellungen.getEinstellung().getDateinamenmuster(), "PDF").get());

      final String s = fd.open();

      if (s == null || s.length() == 0)
      {
        return;
      }

      final File file = new File(s);
      settings.setAttribute("lastdir", file.getParent());
      auswertungSaldoPDF(zeile, file, (Date) getDatumvon().getValue(),
          (Date) getDatumbis().getValue());
    }
    catch (RemoteException e)
    {
      throw new ApplicationException(
          String.format("Fehler beim Aufbau des Reports: %s", e.getMessage()));
    }
  }

  private void auswertungSaldoPDF(final ArrayList<ProjektSaldoZeile> zeile,
      final File file, final Date datumvon, final Date datumbis)
  {
    BackgroundTask t = new BackgroundTask()
    {
      @Override
      public void run(ProgressMonitor monitor) throws ApplicationException
      {
        try
        {
          new ProjektSaldoPDF(zeile, file, datumvon, datumbis);
          GUI.getCurrentView().reload();
        }
        catch (ApplicationException ae)
        {
          GUI.getStatusBar().setErrorText(ae.getMessage());
          throw ae;
        }
      }

      @Override
      public void interrupt()
      {
        //
      }

      @Override
      public boolean isInterrupted()
      {
        return false;
      }
    };
    Application.getController().start(t);
  }
}
