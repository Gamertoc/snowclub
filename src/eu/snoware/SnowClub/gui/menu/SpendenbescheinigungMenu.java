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
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import eu.snoware.SnowClub.gui.action.SpendenbescheinigungDeleteAction;
import eu.snoware.SnowClub.gui.action.SpendenbescheinigungDuplizierenAction;
import eu.snoware.SnowClub.gui.action.SpendenbescheinigungPrintAction;
import eu.snoware.SnowClub.rmi.Spendenbescheinigung;

/**
 * Kontext-Menu zu den Spendenbescheinigungen.
 */
public class SpendenbescheinigungMenu extends ContextMenu
{

  /**
   * Erzeugt ein Kontext-Menu fuer die Liste der Spendenbescheinigungen.
   */
  public SpendenbescheinigungMenu()
  {
    addItem(new CheckedContextMenuItem("Drucken (Standard)",
        new SpendenbescheinigungPrintAction(true), "file-pdf.png"));
    addItem(new CheckedContextMenuItem("Drucken (individuell)",
        new SpendenbescheinigungPrintAction(false), "file-pdf.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new DuplicateMenuItem("als Vorlage für neue Spende",
        new SpendenbescheinigungDuplizierenAction(), "copy.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new CheckedContextMenuItem("löschen...",
        new SpendenbescheinigungDeleteAction(), "trash-alt.png"));
  }

  private static class DuplicateMenuItem extends CheckedContextMenuItem
  {

    /**
     * @param text
     * @param action
     * @param icon
     *          Optionale Angabe eines Icons.
     */
    private DuplicateMenuItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Spendenbescheinigung)
      {
        return true;
      }
      if (o instanceof Spendenbescheinigung[])
      {
        return false;
      }
      return super.isEnabledFor(o);
    }
  }

}
