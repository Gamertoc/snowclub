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

import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import eu.snoware.SnowClub.gui.action.MailemfaengerDeleteAction;
import eu.snoware.SnowClub.gui.control.MitgliedControl;

/**
 * Kontext-Menu zu den Mails eines Mitgliedes.
 */
public class MitgliedMailMenu extends ContextMenu
{

  /**
   * Erzeugt ein Kontext-Menu fuer die Liste der Mails eines Mitgliedes.
   */
  public MitgliedMailMenu(MitgliedControl mc)
  {
    addItem(new CheckedContextMenuItem("löschen...",
        new MailemfaengerDeleteAction(mc), "trash-alt.png"));
  }
}
