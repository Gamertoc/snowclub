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
package eu.snoware.SnowClub.server.DDLTool.Updates;

import java.sql.Connection;

import de.willuhn.util.ApplicationException;
import de.willuhn.util.ProgressMonitor;
import eu.snoware.SnowClub.server.DDLTool.AbstractDDLUpdate;
import eu.snoware.SnowClub.server.DDLTool.Column;
import eu.snoware.SnowClub.server.DDLTool.Table;

public class Update0394 extends AbstractDDLUpdate
{
  public Update0394(String driver, ProgressMonitor monitor, Connection conn)
  {
    super(driver, monitor, conn);
  }

  @Override
  public void run() throws ApplicationException
  {
    Table t = new Table("sekundaerebeitragsgruppe");
    Column pk = new Column("id", COLTYPE.BIGINT, 10, null, true, true);
    t.add(pk);
    Column mitglied = new Column("mitglied", COLTYPE.BIGINT, 10, null, true,
        false);
    t.add(mitglied);
    Column beitragsgruppe = new Column("beitragsgruppe", COLTYPE.BIGINT, 10,
        null, true, false);
    t.add(beitragsgruppe);
    t.setPrimaryKey(pk);
    execute(this.createTable(t));

    execute(this.createForeignKey("fk_sekundaerbeitragegruppe1",
        "sekundaerebeitragsgruppe", "mitglied", "mitglied", "id", "RESTRICT",
        "NO ACTION"));
    execute(this.createForeignKey("fk_sekundaerbeitragegruppe2",
        "sekundaerebeitragsgruppe", "beitragsgruppe", "beitragsgruppe", "id",
        "RESTRICT", "NO ACTION"));
  }
}
