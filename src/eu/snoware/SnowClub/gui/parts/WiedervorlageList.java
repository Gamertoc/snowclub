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
package eu.snoware.SnowClub.gui.parts;

import java.rmi.RemoteException;

import org.eclipse.swt.widgets.Composite;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBService;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.Part;
import de.willuhn.jameica.gui.formatter.DateFormatter;
import de.willuhn.jameica.gui.formatter.Formatter;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.logging.Logger;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.action.WiedervorlageAction;
import eu.snoware.SnowClub.gui.menu.WiedervorlageMenu;
import eu.snoware.SnowClub.io.Adressbuch.Adressaufbereitung;
import eu.snoware.SnowClub.rmi.Mitglied;
import eu.snoware.SnowClub.rmi.Wiedervorlage;
import eu.snoware.SnowClub.util.SCDateFormatTTMMJJJJ;

public class WiedervorlageList extends TablePart implements Part
{

  private TablePart wiedervorlageList;

  public WiedervorlageList(Action action)
  {
    super(action);
  }

  public Part getWiedervorlageList() throws RemoteException
  {
    DBService service = Einstellungen.getDBService();
    DBIterator<Wiedervorlage> wiedervorlagen = service
        .createList(Wiedervorlage.class);
    wiedervorlagen.setOrder("ORDER BY datum DESC");

    if (wiedervorlageList == null)
    {
      wiedervorlageList = new TablePart(wiedervorlagen,
          new WiedervorlageAction(null));
      wiedervorlageList.addColumn("Name", "mitglied", new Formatter()
      {

        @Override
        public String format(Object o)
        {
          Mitglied m = (Mitglied) o;
          if (m == null)
            return null;
          String name = null;
          try
          {
            name = Adressaufbereitung.getNameVorname(m);
          }
          catch (RemoteException e)
          {
            Logger.error("Fehler", e);
          }
          return name;
        }
      });
      wiedervorlageList.addColumn("Datum", "datum",
          new DateFormatter(new SCDateFormatTTMMJJJJ()));
      wiedervorlageList.addColumn("Vermerk", "vermerk");
      wiedervorlageList.addColumn("Erledigung", "erledigung",
          new DateFormatter(new SCDateFormatTTMMJJJJ()));
      wiedervorlageList
          .setContextMenu(new WiedervorlageMenu(wiedervorlageList));
      wiedervorlageList.setRememberColWidths(true);
      wiedervorlageList.setRememberOrder(true);
      wiedervorlageList.setSummary(true);
    }
    else
    {
      wiedervorlageList.removeAll();
      while (wiedervorlagen.hasNext())
      {
        wiedervorlageList.addItem(wiedervorlagen.next());
      }
    }
    return wiedervorlageList;
  }

  @Override
  public synchronized void paint(Composite parent) throws RemoteException
  {
    super.paint(parent);
  }

}
