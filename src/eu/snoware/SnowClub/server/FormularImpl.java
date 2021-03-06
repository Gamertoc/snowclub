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
package eu.snoware.SnowClub.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.keys.FormularArt;
import eu.snoware.SnowClub.rmi.Formular;

public class FormularImpl extends AbstractDBObject implements Formular
{

  private static final long serialVersionUID = 1603994510932244220L;

  public FormularImpl() throws RemoteException
  {
    super();
  }

  @Override
  protected String getTableName()
  {
    return "formular";
  }

  @Override
  public String getPrimaryAttribute()
  {
    return "bezeichnung";
  }

  @Override
  protected void deleteCheck()
  {
    //
  }

  @Override
  protected void insertCheck() throws ApplicationException
  {
    try
    {
      if (getInhalt() == null)
      {
        throw new ApplicationException("Bitte g?ltigen Dateinamen angeben!");
      }
      DBIterator<Formular> it = Einstellungen.getDBService()
          .createList(Formular.class);
      it.addFilter("bezeichnung = ?", getBezeichnung());
      if (it.hasNext())
      {
        throw new ApplicationException(
            "Diese Bezeichnung wird schon verwendet, bitte eine Andere verwenden.");
      }
    }
    catch (RemoteException e)
    {
      Logger.error("Fehler", e);
    }
    updateCheck();
  }

  @Override
  protected void updateCheck() throws ApplicationException
  {
    try
    {
      if (getBezeichnung() == null || getBezeichnung().length() == 0)
      {
        throw new ApplicationException(
            "Bitte eine eindeutige Bezeichnung eingeben");
      }
    }
    catch (RemoteException e)
    {
      String fehler = "Formularfeld kann nicht gespeichert werden. Siehe system log";
      Logger.error(fehler, e);
      throw new ApplicationException(fehler);
    }
  }

  @Override
  protected Class<?> getForeignObject(String arg0)
  {
    return null;
  }

  @Override
  public String getBezeichnung() throws RemoteException
  {
    return (String) getAttribute("bezeichnung");
  }

  @Override
  public void setBezeichnung(String bezeichnung) throws RemoteException
  {
    setAttribute("bezeichnung", bezeichnung);
  }

  @Override
  public byte[] getInhalt() throws RemoteException
  {
    return (byte[]) this.getAttribute("inhalt");
  }

  @Override
  public void setInhalt(byte[] inhalt) throws RemoteException
  {
    setAttribute("inhalt", inhalt);
  }

  @Override
  public FormularArt getArt() throws RemoteException
  {
    Integer art = (Integer) getAttribute("art");
    if (art == null)
    {
      return null;
    }
    for (FormularArt form : FormularArt.values())
    {
      if (form.getKey() == art)
      {
        return form;
      }
    }
    return null;
  }

  @Override
  public void setArt(FormularArt art) throws RemoteException
  {
    setAttribute("art", art == null ? 0 : art.getKey());
  }

  @Override
  public Object getAttribute(String fieldName) throws RemoteException
  {
    return super.getAttribute(fieldName);
  }

}
