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

import java.rmi.RemoteException;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.logging.Logger;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.action.MitgliedskontoDetailAction;
import eu.snoware.SnowClub.gui.action.MitgliedskontoDetailSollLoeschenAction;
import eu.snoware.SnowClub.gui.action.MitgliedskontoDetailSollNeuAction;
import eu.snoware.SnowClub.gui.action.MitgliedskontoIstLoesenAction;
import eu.snoware.SnowClub.gui.action.SpendenbescheinigungAction;
import eu.snoware.SnowClub.gui.control.MitgliedskontoNode;
import eu.snoware.SnowClub.rmi.Buchung;

/**
 * Kontext-Menu, welches an MitgliedskontenListen gehangen werden kann.
 */
public class MitgliedskontoMenu extends ContextMenu
{

  /**
   * Erzeugt ein Kontext-Menu fuer eine Liste von Mitgliedskonten.
   */
  public MitgliedskontoMenu()
  {
    addItem(new MitgliedItem("neue Sollbuchung",
        new MitgliedskontoDetailSollNeuAction(), "calculator.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new SollItem("Sollbuchung bearbeiten",
        new MitgliedskontoDetailAction(), "calculator.png"));
    addItem(new SollOhneIstItem("Sollbuchung löschen",
        new MitgliedskontoDetailSollLoeschenAction(), "calculator.png"));
    addItem(new SollMitIstItem("Istbuchung vom Mitgliedskonto lösen",
        new MitgliedskontoIstLoesenAction(), "calculator.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new SpendenbescheinigungItem("Spendenbescheinigung erstellen",
        new SpendenbescheinigungAction(), "calculator.png"));
  }

  private static class MitgliedItem extends CheckedContextMenuItem
  {

    /**
     * @param text
     * @param action
     */
    private MitgliedItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof MitgliedskontoNode)
      {
        MitgliedskontoNode mkn = (MitgliedskontoNode) o;
        if (mkn.getType() == MitgliedskontoNode.MITGLIED)
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

  private static class SollItem extends CheckedContextMenuItem
  {

    /**
     * @param text
     * @param action
     */
    private SollItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof MitgliedskontoNode)
      {
        MitgliedskontoNode mkn = (MitgliedskontoNode) o;
        if (mkn.getType() == MitgliedskontoNode.SOLL)
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

  private static class SollOhneIstItem extends CheckedContextMenuItem
  {

    private SollOhneIstItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof MitgliedskontoNode)
      {
        MitgliedskontoNode mkn = (MitgliedskontoNode) o;
        if (mkn.getType() == MitgliedskontoNode.SOLL)
        {
          DBIterator<Buchung> it;
          try
          {
            it = Einstellungen.getDBService().createList(Buchung.class);
            it.addFilter("mitgliedskonto = ?", new Object[] { mkn.getID() });
            if (it.size() == 0)
            {
              return true;
            }
          }
          catch (RemoteException e)
          {
            Logger.error("Fehler", e);
          }
          return false;
        }
        else
        {
          return false;
        }
      }
      return super.isEnabledFor(o);
    }
  }

  private static class SollMitIstItem extends CheckedContextMenuItem
  {

    /**
     * @param text
     * @param action
     */
    private SollMitIstItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof MitgliedskontoNode)
      {
        MitgliedskontoNode mkn = (MitgliedskontoNode) o;
        if (mkn.getType() == MitgliedskontoNode.IST)
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

  private static class SpendenbescheinigungItem extends CheckedContextMenuItem
  {

    private SpendenbescheinigungItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof MitgliedskontoNode)
      {
        MitgliedskontoNode mkn = (MitgliedskontoNode) o;
        if (mkn.getType() == MitgliedskontoNode.IST
            || mkn.getType() == MitgliedskontoNode.MITGLIED)
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
