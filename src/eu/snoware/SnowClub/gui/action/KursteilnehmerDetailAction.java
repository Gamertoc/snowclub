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
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.view.KursteilnehmerDetailView;
import eu.snoware.SnowClub.rmi.Kursteilnehmer;

public class KursteilnehmerDetailAction implements Action
{

  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    Kursteilnehmer kt = null;

    if (context != null && (context instanceof Kursteilnehmer))
    {
      kt = (Kursteilnehmer) context;
    }
    else
    {
      try
      {
        kt = (Kursteilnehmer) Einstellungen.getDBService().createObject(
            Kursteilnehmer.class, null);
      }
      catch (RemoteException e)
      {
        throw new ApplicationException(
            "Fehler bei der Erzeugung eines neuen Kursteilnehmers", e);
      }
    }
    GUI.startView(KursteilnehmerDetailView.class.getName(), kt);
  }
}
