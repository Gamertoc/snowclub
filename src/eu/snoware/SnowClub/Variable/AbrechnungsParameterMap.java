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
package eu.snoware.SnowClub.Variable;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import eu.snoware.SnowClub.gui.input.AbbuchungsmodusInput;
import eu.snoware.SnowClub.io.AbrechnungSEPAParam;
import eu.snoware.SnowClub.keys.Monat;
import eu.snoware.SnowClub.util.SCDateFormatTTMMJJJJ;

public class AbrechnungsParameterMap
{

  public AbrechnungsParameterMap()
  {

  }

  public Map<String, Object> getMap(AbrechnungSEPAParam param,
      Map<String, Object> inma) throws RemoteException
  {
    Map<String, Object> map = null;
    if (inma == null)
    {
      map = new HashMap<>();
    }
    else
    {
      map = inma;
    }
    map.put(AbrechnungsParameterVar.ABBUCHUNGSMODUS.getName(),
        new AbbuchungsmodusInput(param.abbuchungsmodus));
    map.put(AbrechnungsParameterVar.ABRECHNUNGSMONAT.getName(),
        Monat.getByKey(param.abrechnungsmonat));
    map.put(AbrechnungsParameterVar.FAELLIGKEIT1.getName(),
        new SCDateFormatTTMMJJJJ().format(param.faelligkeit1));
    map.put(AbrechnungsParameterVar.FAELLIGKEIT2.getName(),
        new SCDateFormatTTMMJJJJ().format(param.faelligkeit2));
    map.put(AbrechnungsParameterVar.KOMPAKTEABBUCHUNG.getName(),
        param.kompakteabbuchung ? "J" : "N");
    map.put(AbrechnungsParameterVar.KURSTEILNEHMER.getName(),
        param.kursteilnehmer ? "J" : "N");
    map.put(AbrechnungsParameterVar.SEPAPRINT.getName(),
        param.sepaprint ? "J" : "N");
    map.put(AbrechnungsParameterVar.STICHTAG.getName(),
        new SCDateFormatTTMMJJJJ().format(param.stichtag));
    map.put(AbrechnungsParameterVar.VERWENDUNGSZWECK.getName(),
        param.verwendungszweck);
    if (param.vondatum != null)
    {
      map.put(AbrechnungsParameterVar.VONDATUM.getName(),
          new SCDateFormatTTMMJJJJ().format(param.vondatum));
    }
    if (param.bisdatum != null)
    {
      map.put(AbrechnungsParameterVar.BISDATUM.getName(),
          new SCDateFormatTTMMJJJJ().format(param.bisdatum));
    }
    map.put(AbrechnungsParameterVar.ZUSATZBETRAEGE.getName(),
        param.zusatzbetraege ? "J" : "N");
    return map;
  }
}
