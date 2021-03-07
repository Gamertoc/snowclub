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

import java.rmi.RemoteException;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.YesNoDialog;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.Messaging.BuchungMessage;
import eu.snoware.SnowClub.rmi.Buchung;
import eu.snoware.SnowClub.rmi.Jahresabschluss;
import eu.snoware.SnowClub.util.JVDateFormatTTMMJJJJ;

/**
 * Loeschen einer Buchung.
 */
public class BuchungDeleteAction implements Action
{
  private boolean splitbuchung;

  public BuchungDeleteAction(boolean splitbuchung)
  {
    this.splitbuchung = splitbuchung;
  }

  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    if (context == null
        || (!(context instanceof Buchung) && !(context instanceof Buchung[])))
    {
      throw new ApplicationException("Keine Buchung ausgew�hlt");
    }
    try
    {
      Buchung[] b = null;
      if (context instanceof Buchung)
      {
        b = new Buchung[1];
        b[0] = (Buchung) context;
      }
      else if (context instanceof Buchung[])
      {
        b = (Buchung[]) context;
      }
      if (b == null)
      {
        return;
      }
      if (b.length == 0)
      {
        return;
      }
      if (b[0].isNewObject())
      {
        return;
      }
      YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
      d.setTitle("Buchung" + (b.length > 1 ? "en" : "") + " l�schen");
      d.setText("Wollen Sie diese Buchung" + (b.length > 1 ? "en" : "")
          + " wirklich l�schen?");
      try
      {
        Boolean choice = (Boolean) d.open();
        if (!choice.booleanValue())
        {
          return;
        }
      }
      catch (Exception e)
      {
        Logger.error("Fehler beim L�schen der Buchung", e);
        return;
      }
      int count = 0;
      for (Buchung bu : b)
      {
        Jahresabschluss ja = bu.getJahresabschluss();
        if (ja != null)
        {
          throw new ApplicationException(String.format(
              "Buchung wurde bereits am %s von %s abgeschlossen.",
              new JVDateFormatTTMMJJJJ().format(ja.getDatum()), ja.getName()));
        }
        if (bu.getSplitId() == null)
        {
          bu.delete();
          count++;
        }
        else if (splitbuchung)
        {
          bu.setDelete(true);
          Application.getMessagingFactory().sendMessage(new BuchungMessage(bu));
          count++;
        }
      }
      if (count > 0)
      {
        GUI.getStatusBar().setSuccessText(
            String.format("%d Buchung" + (b.length != 1 ? "en" : "")
                + " gel�scht.", count));
      }
      else
      {
        GUI.getStatusBar().setErrorText("Keine Buchung gel�scht");
      }
    }
    catch (RemoteException e)
    {
      String fehler = "Fehler beim L�schen der Buchung.";
      GUI.getStatusBar().setErrorText(fehler);
      Logger.error(fehler, e);
    }
  }
}
