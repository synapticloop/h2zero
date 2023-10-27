package synapticloop.h2zero.sqlite3.finders;

import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import synapticloop.h2zero.sqlite3.DatabaseTest;
import synapticloop.sample.h2zero.sqlite3.finder.AuthorFinder;
import synapticloop.sample.h2zero.sqlite3.inserter.AuthorInserter;
import synapticloop.sample.h2zero.sqlite3.model.Author;
import synapticloop.sample.h2zero.sqlite3.model.AuthorStatus;

import java.sql.SQLException;
import java.util.List;

public class FindAllTest extends DatabaseTest {
  @Override
  public void setup() {
    super.setup();

    // now we want to add some authors
    AuthorInserter.insertSilent(
        null,
        "txt_id_author_1",
        "txt_nm_author_1",
        "txt_nm_username_1",
        "txt_bio_1",
        "txt_url_cache_image");
  }

  @Test
  public void testFindAll() {
    try {
      List<Author> authors = AuthorFinder.findAll();
      Assert.assertEquals(1, authors.size());
    } catch (SQLException e) {
      throw new AssertionFailedError("error in SQL generation for AuthorFinder.findAll()");
    }
  }
}
