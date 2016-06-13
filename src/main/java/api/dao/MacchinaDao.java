package api.dao;

import api.dao.mapper.MacchinaResultSetMapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by NICOLA on 13/06/2016.
 */

@RegisterMapper(MacchinaResultSetMapper.class)
public interface MacchinaDao {


}
