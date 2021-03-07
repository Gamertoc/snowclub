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
import de.willuhn.logging.Logger;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.control.BuchungsControl;
import eu.snoware.SnowClub.gui.view.BuchungView;
import eu.snoware.SnowClub.rmi.Buchung;
import eu.snoware.SnowClub.rmi.Konto;

public class BuchungNeuAction implements Action
{
  @Override
  public void handleAction(Object context)
  {
    Buchung buch;
    try
    {
      buch = (Buchung) Einstellungen.getDBService().createObject(Buchung.class,
          null);
      if (context instanceof BuchungsControl)
      {
        BuchungsControl control = (BuchungsControl) context;
        Konto konto = (Konto) control.getSuchKonto().getValue();
        if (null != konto)
          buch.setKonto(konto);
      }
      GUI.startView(BuchungView.class, buch);
    }
    catch (RemoteException e)
    {
      Logger.error("Fehler", e);
    }
  }
}
