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

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.CheckedSingleContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.Messaging.BuchungMessage;
import eu.snoware.SnowClub.gui.action.BuchungAction;
import eu.snoware.SnowClub.gui.action.BuchungBuchungsartZuordnungAction;
import eu.snoware.SnowClub.gui.action.BuchungDeleteAction;
import eu.snoware.SnowClub.gui.action.BuchungKontoauszugZuordnungAction;
import eu.snoware.SnowClub.gui.action.BuchungMitgliedskontoZuordnungAction;
import eu.snoware.SnowClub.gui.action.BuchungProjektZuordnungAction;
import eu.snoware.SnowClub.gui.control.BuchungsControl;
import eu.snoware.SnowClub.keys.SplitbuchungTyp;
import eu.snoware.SnowClub.rmi.Buchung;

public class SplitBuchungMenu extends ContextMenu
{
  /**
   * Erzeugt ein Kontext-Menu fuer die Liste der Splitbuchungen.
   */

  public SplitBuchungMenu(BuchungsControl control)
  {
    addItem(new CheckedSplitBuchungItem("bearbeiten", new BuchungAction(true),
        "edit.png"));
    addItem(new CheckedSplitBuchungItem("Buchungsart zuordnen",
        new BuchungBuchungsartZuordnungAction(control), "zuordnung.png"));
    addItem(new CheckedSplitBuchungItem("Mitgliedskonto zuordnen",
        new BuchungMitgliedskontoZuordnungAction(control), "exchange-alt.png"));
    addItem(new CheckedSplitBuchungItem("Projekt zuordnen",
        new BuchungProjektZuordnungAction(control), "exchange-alt.png"));
    addItem(new CheckedSplitBuchungItem("Kontoauszug zuordnen",
        new BuchungKontoauszugZuordnungAction(control), "zuordnung.png"));
    addItem(new DeleteSplitBuchungItem());
    addItem(new RestoreSplitBuchungItem());
  }

  private static class CheckedSplitBuchungItem
      extends CheckedSingleContextMenuItem
  {
    private CheckedSplitBuchungItem(String text, Action action, String icon)
    {
      super(text, action, icon);
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Buchung)
      {
        try
        {
          return !((Buchung) o).isToDelete();
        }
        catch (RemoteException e)
        {
          Logger.error("Fehler", e);
        }
      }
      return false;
    }
  }

  private static class DeleteSplitBuchungItem extends CheckedContextMenuItem
  {
    private DeleteSplitBuchungItem()
    {
      super("löschen...", new BuchungDeleteAction(true), "trash-alt.png");
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Buchung)
      {
        Buchung b = (Buchung) o;
        try
        {
          return !b.isToDelete() && b.getSplitTyp() == SplitbuchungTyp.SPLIT;
        }
        catch (RemoteException e)
        {
          Logger.error("Fehler", e);
        }
      }
      return false;
    }
  }

  private static class RestoreSplitBuchungItem extends CheckedContextMenuItem
  {
    private RestoreSplitBuchungItem()
    {
      super("wiederherstellen", new Action()
      {
        @Override
        public void handleAction(Object context) throws ApplicationException
        {
          if (context == null || !(context instanceof Buchung))
          {
            throw new ApplicationException("Keine Buchung ausgewählt");
          }
          try
          {
            Buchung bu = (Buchung) context;
            bu.setDelete(false);
            Application.getMessagingFactory()
                .sendMessage(new BuchungMessage(bu));
          }
          catch (RemoteException e)
          {
            String fehler = "Fehler beim Wiederherstellen der Buchung.";
            GUI.getStatusBar().setErrorText(fehler);
            Logger.error(fehler, e);
          }
        }
      }, "undo.png");
    }

    @Override
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Buchung)
      {
        try
        {
          return ((Buchung) o).isToDelete();
        }
        catch (RemoteException e)
        {
          Logger.error("Fehler", e);
        }
      }
      return false;
    }
  }
}
