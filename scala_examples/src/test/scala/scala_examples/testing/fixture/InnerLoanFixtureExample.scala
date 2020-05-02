package scala_examples.testing.fixture

import java.util.concurrent.ConcurrentHashMap

import org.scalatest.flatspec.AnyFlatSpec

// Fixture example using HOFs wihout test framework interaction
object InnerLoanFixtureExample {

  object DbServer { // Simulating a database server
    type Db = StringBuffer
    private val databases = new ConcurrentHashMap[String, Db]
    def createDb(name: String): Db = {
      val db = new StringBuffer
      databases.put(name, db)
      db
    }
    def removeDb(name: String): Unit = {
      databases.remove(name)
    }
  }

}

import java.io._
import java.util.UUID.randomUUID

import scala_examples.testing.fixture.InnerLoanFixtureExample.DbServer._

class InnerLoanFixtureExample extends AnyFlatSpec {

  def withDatabase(testCode: Db => Any): Unit = {
    val dbName = randomUUID.toString
    val db = createDb(dbName) // create the fixture
    try {
      db.append("ScalaTest is ") // perform setup
      testCode(db) // "loan" the fixture to the test
    } finally removeDb(dbName) // clean up the fixture
  }

  def withFile(testCode: (File, FileWriter) => Any): Unit = {
    val file = File.createTempFile("hello", "world") // create the fixture
    val writer = new FileWriter(file)
    try {
      writer.write("ScalaTest is ") // set up the fixture
      testCode(file, writer) // "loan" the fixture to the test
    } finally writer.close() // clean up the fixture
  }

  // This test needs the file fixture
  "Testing" should "be productive" in withFile { (file, writer) =>
    writer.write("productive!")
    writer.flush()
    assert(file.length === 24)
  }

  // This test needs the database fixture
  "Test code" should "be readable" in withDatabase { db =>
    db.append("readable!")
    assert(db.toString === "ScalaTest is readable!")
  }

  // This test needs both the file and the database
  it should "be clear and concise" in withDatabase { db =>
    withFile { (file, writer) => // loan-fixture methods compose
      db.append("clear!")
      writer.write("concise!")
      writer.flush()
      assert(db.toString === "ScalaTest is clear!")
      assert(file.length === 21)
    }
  }
}
