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

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import eu.snoware.SnowClub.gui.action.FamilienmitgliedEntfernenAction;
import eu.snoware.SnowClub.gui.control.FamilienbeitragNode;

/**
 * Kontext-Menu zu den Famlienbeitr�gen.
 */
public class FamilienbeitragMenu extends ContextMenu
{

  public FamilienbeitragMenu()
  {
    addItem(new AngehoerigerItem("Aus Familienverband entfernen",
        new FamilienmitgliedEntfernenAction(), "trash-alt.png"));
  }

  private static class AngehoerigerItem extends CheckedContextMenuItem
  {

    /**
     * @param text
     * @param action
     */
    private AngehoerigerItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof FamilienbeitragNode)
      {
        FamilienbeitragNode fbn = (FamilienbeitragNode) o;
        if (fbn.getType() == FamilienbeitragNode.ANGEHOERIGER)
        {
          return true;
        }
        else
        {
          return false;
        }
      }
      return super.isEnabledFor(o);
    }
  }

}
