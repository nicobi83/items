package api.dao;

import api.dao.mapper.ItemResultSetMapper;
import api.model.Items;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;
import java.util.Set;

@RegisterMapper(ItemResultSetMapper.class)
//richiama ItemResultSetMapper
public interface ItemDao {

    @SqlQuery("SELECT * FROM items WHERE id = :id")
    @Mapper(ItemResultSetMapper.class)
    Items.Item getItem(@Bind("id") String id);

// BIND DICE A JDBC CHE QUANDO FA GET ITEM SOSTITUISCE ID CON LA STRING ID, SUBITO DOPO I : SA GIA' COSA FARE

    //il bind si va a sostituire a :id


    @SqlQuery("SELECT * FROM items")
    Set<Items.Item> listAll();

    @SqlQuery("SELECT * FROM items WHERE " +
            " UPPER(id) LIKE UPPER(:id) AND " +
            " UPPER(name) LIKE UPPER(:name) AND " +
            " UPPER(description) LIKE UPPER(:description) AND " +
            " UPPER(content) LIKE UPPER(:content) AND " +
            " date_modified <= :date_modified_before AND " +
            " date_modified >= :date_modified_after AND " +
            " date_created <= :date_created_before AND " +
            " date_created >= :date_created_after" +
            "")
    Items.Item find(
            @Bind("id") String id,
            @Bind("name") String name,
            @Bind("description") String description,
            @Bind("content") String content,
            @Bind("date_modified_after") Date date_modified_after,
            @Bind("date_modified_before") Date date_modified_before,
            @Bind("date_created_after") Date date_created_after,
            @Bind("date_created_before") Date date_created_before
    );

    @SqlUpdate("INSERT INTO items (id, name, description, content, date_modified, date_created)" +
            " VALUES (:i.id, :i.name, :i.description, :i.content, :i.modifiedDate, :i.creationDate)")

    int insert(@BindBean("i")Items.Item item);
    //bindbean indica che i sarebbe l'oggetto quindi chiama il getter e setter dell'oggetto
    //bind per il singolo valore, bindbean per prendere tutto l'oggetto


    @SqlUpdate("INSERT INTO items (id, name, description, content, date_modified, date_created, values)" +
            " VALUES (:i.id, :i.name, :i.description, :i.content, :i.modifiedDate, :i.creationDate, :values)")
    int insert(@BindBean("i") Items.Item item, @Bind("values") String s);

    @SqlUpdate("UPDATE items " +
            " SET name = :i.name," +
            " description = :i.description, " +
            " content = :i.content, " +
            " date_modified = :i.modifiedDate" +
            " WHERE id = :i.id")
    int update(@BindBean("i") Items.Item item);


    //sqlupdate se cancello o modifico i dati altrimenti per altre cose sqlquery

    @SqlUpdate("UPDATE items " +
            " SET name = :i.name," +
            " description = :i.description, " +
            " content = :i.content, " +
            " values = :values, " +
            " date_modified = :i.modifiedDate" +
            " WHERE id = :i.id")
    int update(@BindBean("i") Items.Item item, @Bind("values") String values);


    @SqlUpdate("DELETE FROM items " +
            " WHERE id = :i.id")
    int delete(@BindBean("i") Items.Item item);

    @SqlUpdate("DELETE FROM items " +
            " WHERE id = :id")
    int delete(@Bind("id") String id);

    @SqlUpdate("DELETE FROM items " +
            " WHERE name = :name")
    int deleteByName(@Bind("name") String name);

    @SqlQuery("SELECT values FROM items WHERE id = :id")
    String getValues(@Bind("id") String id);
}
//INSERISCI E MODIFICA DATI SI USA @SQLUPDATE
// RICHIAMA LA CLASSE @RegisterMapper(ItemResultSetMapper.class)
