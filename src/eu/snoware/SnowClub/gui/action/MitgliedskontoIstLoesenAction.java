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
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.Messaging.MitgliedskontoMessage;
import eu.snoware.SnowClub.gui.control.MitgliedskontoNode;
import eu.snoware.SnowClub.rmi.Buchung;

public class MitgliedskontoIstLoesenAction implements Action
{

  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
    d.setTitle("Istbuchung vom Mitgliedskonto l?sen");
    d.setText("Wollen Sie die Istbuchung wirklich vom Mitgliedskonto l?sen?");

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
      Logger.error("Fehler", e);
      return;
    }
    MitgliedskontoNode mkn = null;
    Buchung bu = null;

    if (context != null && (context instanceof MitgliedskontoNode))
    {
      mkn = (MitgliedskontoNode) context;
      try
      {
        bu = (Buchung) Einstellungen.getDBService().createObject(Buchung.class,
            mkn.getID());
        bu.setMitgliedskonto(null);
        bu.store();
        GUI.getStatusBar().setSuccessText(

        "Istbuchung vom Mitgliedskonto gel?st.");
        Application.getMessagingFactory().sendMessage(
            new MitgliedskontoMessage(mkn.getMitglied()));
      }
      catch (RemoteException e)
      {
        throw new ApplicationException(
            "Fehler beim l?sen der Istbuchung vom Mitgliedskonto");
      }
    }
  }
}
