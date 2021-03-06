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

package eu.snoware.SnowClub.gui.dialogs;

import java.rmi.RemoteException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.jameica.gui.input.SelectInput;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.system.OperationCanceledException;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.rmi.Buchung;
import eu.snoware.SnowClub.rmi.Projekt;

/**
 * Ein Dialog, ueber den man ein Projekt ausw?hlen kann.
 */
public class ProjektAuswahlDialog extends AbstractDialog<Projekt>
{

  private Projekt selected = null;

  private SelectInput projekte = null;

  Buchung[] buchungen = null;

  public ProjektAuswahlDialog(int position, Buchung[] buchungen)
  {
    super(position);
    this.buchungen = buchungen;

    setTitle("Projekt ausw??hlen");
    setSize(450, 150);
  }

  @Override
  protected void paint(Composite parent) throws Exception
  {
    LabelGroup options = new LabelGroup(parent, "Projekte");
    options.addInput(this.getProjekte());
    ButtonArea b = new ButtonArea();
    b.addButton("weiter", new Action()
    {

      @Override
      public void handleAction(Object context)
      {
        selected = (Projekt) projekte.getValue();
        close();
      }
    });
    b.addButton("abbrechen", new Action()
    {

      @Override
      public void handleAction(Object context)
      {
        throw new OperationCanceledException();
      }
    });
    b.paint(parent);
  }

  @Override
  protected Projekt getData() throws Exception
  {
    return this.selected;
  }

  private SelectInput getProjekte() throws RemoteException
  {
    if (this.projekte != null)
    {
      return this.projekte;
    }
    DBIterator<Projekt> pj = Einstellungen.getDBService()
        .createList(Projekt.class);
    String select = "((startdatum is null or startdatum <= ?) and (endedatum is null or endedatum >= ?)) ";

    if (buchungen != null)
    {
      /*
       * UND-Verkn?pfung der Datumsbereiche, damit nur Projekte angezeigt
       * werden, die f?r die Auswahl g?ltig sind
       */
      for (Buchung buchung : buchungen)
      {
        pj.addFilter(select,
            new Object[] { buchung.getDatum(), buchung.getDatum() });
      }
    }

    pj.setOrder("ORDER BY bezeichnung");
    this.projekte = new SelectInput(pj, null);
    this.projekte.setName("Projekt");
    this.projekte.addListener(new Listener()
    {

      @Override
      public void handleEvent(Event event)
      {
        selected = (Projekt) projekte.getValue();
      }
    });
    return this.projekte;
  }
}
