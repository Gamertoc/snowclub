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
package eu.snoware.SnowClub.rmi;

import java.rmi.RemoteException;

import de.willuhn.datasource.rmi.DBObject;

public interface MailAnhang extends DBObject
{
  /**
   * ID der zugeh�rigen Mail
   */
  public Mail getMail() throws RemoteException;

  /**
   * ID der zugeh�rigen Mail
   */
  public void setMail(Mail mail) throws RemoteException;

  public byte[] getAnhang() throws RemoteException;

  public void setAnhang(byte[] anhang) throws RemoteException;

  public String getDateiname() throws RemoteException;

  public void setDateiname(String dateiname) throws RemoteException;

}