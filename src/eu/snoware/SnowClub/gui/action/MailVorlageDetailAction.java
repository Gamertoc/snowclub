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
import eu.snoware.SnowClub.gui.view.MailVorlageDetailView;
import eu.snoware.SnowClub.rmi.MailVorlage;

public class MailVorlageDetailAction implements Action
{

  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    MailVorlage mv = null;

    if (context != null && (context instanceof MailVorlage))
    {
      mv = (MailVorlage) context;
    }
    else
    {
      try
      {
        mv = (MailVorlage) Einstellungen.getDBService().createObject(
            MailVorlage.class, null);
      }
      catch (RemoteException e)
      {
        throw new ApplicationException(
            "Fehler bei der Erzeugung der neuen MailVorlage", e);
      }
    }
    GUI.startView(MailVorlageDetailView.class.getName(), mv);
  }
}
