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
import eu.snoware.SnowClub.gui.action.SuchprofilDeleteAction;
import eu.snoware.SnowClub.gui.action.SuchprofilLadenAction;
import eu.snoware.SnowClub.gui.control.MitgliedSuchProfilControl;

/**
 * Kontext-Menu zu den Suchprofilen.
 */
public class SuchprofilMenu extends ContextMenu
{
  public SuchprofilMenu(MitgliedSuchProfilControl control)
  {
    addItem(new CheckedContextMenuItem("laden", new SuchprofilLadenAction(),
        "document-open.png"));
    addItem(new CheckedContextMenuItem("l?schen...",
        new SuchprofilDeleteAction(control), "trash-alt.png"));
  }
}
