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
import de.willuhn.jameica.system.OperationCanceledException;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.dialogs.KontoAuswahlDialog;
import eu.snoware.SnowClub.gui.view.AnfangsbestandView;
import eu.snoware.SnowClub.rmi.Anfangsbestand;
import eu.snoware.SnowClub.rmi.Konto;

public class AnfangsbestandNeuAction implements Action
{
  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    Anfangsbestand anf;
    try
    {
      anf = (Anfangsbestand) Einstellungen.getDBService().createObject(
          Anfangsbestand.class, null);
      Konto k;
      if (context instanceof Konto)
      {
        k = (Konto) context;
        anf.setKonto(k);
      }
      else
      {
        KontoAuswahlDialog d = new KontoAuswahlDialog(
            KontoAuswahlDialog.POSITION_CENTER, false, false, true);
        try
        {
          context = d.open();
          if (context == null)
          {
            GUI.getStatusBar().setErrorText(
                "Kein Konto ausgew?hlt. Vorgang abgebrochen.");
            return;
          }
          k = (Konto) context;
          anf.setKonto(k);
        }
        catch (OperationCanceledException oce)
        {
          GUI.getStatusBar().setErrorText("Vorgang abgebrochen");
          return;
        }
        catch (Exception e)
        {
          Logger.error("error while choosing konto", e);
          GUI.getStatusBar().setErrorText("Fehler bei der Auswahl des Kontos.");
        }
      }
      GUI.startView(AnfangsbestandView.class, anf);
    }
    catch (RemoteException e)
    {
      throw new ApplicationException("Kann kein Anfangsbestand-Objekt erzeugen");
    }
  }
}
