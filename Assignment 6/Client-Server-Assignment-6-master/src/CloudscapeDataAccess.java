import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CloudscapeDataAccess
        implements AddressBookDataAccess {

    // reference to database connection
    private Connection connection;

    // reference to prepared statement for locating entry
    private PreparedStatement sqlFind;
    /******************************************/
    private PreparedStatement testsqlFind;
    /*******************************************/
    // reference to prepared statement for determining personID
    private PreparedStatement sqlPersonID;

    // references to prepared statements for inserting entry
    private PreparedStatement sqlInsertName;
    private PreparedStatement sqlInsertAddress;
    private PreparedStatement sqlInsertPhone;
    private PreparedStatement sqlInsertEmail;
    /******************************************/
    private PreparedStatement testsqlInsertAddress;
    /*******************************************/

    // references to prepared statements for updating entry
    private PreparedStatement sqlUpdateName;
    private PreparedStatement sqlUpdateAddress;
    private PreparedStatement sqlUpdatePhone;
    private PreparedStatement sqlUpdateEmail;
    /*******************************************/
    private PreparedStatement testsqlUpdateAddress;
    private PreparedStatement testsqlUpdatePhone;
    private PreparedStatement testsqlUpdateEmail;
    /*****************************************/

    // references to prepared statements for updating entry
    private PreparedStatement sqlDeleteName;
    private PreparedStatement sqlDeleteAddress;
    private PreparedStatement sqlDeletePhone;
    private PreparedStatement sqlDeleteEmail;

    // set up PreparedStatements to access database
    public CloudscapeDataAccess() throws Exception
    {
        // connect to addressbook database
        connect();

        // locate person
        sqlFind = connection.prepareStatement(
                "SELECT names.personID, firstName, lastName, " +
                        "addressID, address1, address2, city, county, " +
                        "phoneID, phoneNumber, emailID, " +
                        "emailAddress " +
                        "FROM names, addresses, phoneNumbers, emailAddresses " +
                        "WHERE lastName = ? AND " +
                        "names.personID = addresses.personID AND " +
                        "names.personID = phoneNumbers.personID AND " +
                        "names.personID = emailAddresses.personID" );

        testsqlFind = connection.prepareStatement(
                "SELECT names.personID, firstName, lastName, " +
                        "addressID, address1, address2, city, county, " +
                        "altaddress1, altaddress2, altcity, altcounty, " +
                        "phoneID, phoneNumber, altphoneNumber emailID, " +
                        "emailAddress, altemailAddress " +
                        "FROM names, addresses, phoneNumbers, emailAddresses " +
                        "WHERE lastName = ? AND " +
                        "names.personID = addresses.personID AND " +
                        "names.personID = phoneNumbers.personID AND " +
                        "names.personID = emailAddresses.personID" );
        //    select count(id), name
        //    from programparticipants
        //    group by name
        //    having count(id) > 1
//           sqltestFind = connection.prepareStatement(
//           "SELECT names.personID, firstName, lastName, " +
//                   "addressID, address1, address2, city, county, " +
//                   "phoneID, phoneNumber, emailID, " +
//                   "emailAddress " +
//
//                   "FROM names, addresses, phoneNumbers, emailAddresses " +
//                   "WHERE lastName = ?");

        // Obtain personID for last person inserted in database.
        // [This is a Cloudscape-specific database operation.]
        //sqlPersonID = connection.prepareStatement(
        // "VALUES ConnectionInfo.lastAutoincrementValue( " +
        //    "'APP', 'NAMES', 'PERSONID')" );
        sqlPersonID =connection.prepareStatement("select last_insert_id()");
        // Insert first and last names in table names.
        // For referential integrity, this must be performed
        // before sqlInsertAddress, sqlInsertPhone and
        // sqlInsertEmail.
        sqlInsertName = connection.prepareStatement(
                "INSERT INTO names ( firstName, lastName ) " +
                        "VALUES ( ? , ? )" );

        // insert address in table addresses
        sqlInsertAddress = connection.prepareStatement(
                "INSERT INTO addresses ( personID, address1, " +
                        "address2, city, county ) " +
                        "VALUES ( ? , ? , ? , ? , ? )" );

        /***************************************************/
        testsqlInsertAddress = connection.prepareStatement(
                "INSERT INTO addresses ( personID, address1, " +
                        "address2, city, county," +
                        "altaddress1, " +
                        "altaddress2, altcity, altcounty ) " +
                        "VALUES ( ? , ? , ? , ? , ?, ? , ? , ? , ? )" );
/***************************************************/

        // insert phone number in table phoneNumbers
        sqlInsertPhone = connection.prepareStatement(
                "INSERT INTO phoneNumbers " +
                        "( personID, phoneNumber, altphoneNumber) " +
                        "VALUES ( ? , ? , ?)" );

        // insert email in table emailAddresses
        sqlInsertEmail = connection.prepareStatement(
                "INSERT INTO emailAddresses " +
                        "( personID, emailAddress, altemailAddress) " +
                        "VALUES ( ? , ? , ?)" );

        // update first and last names in table names
        sqlUpdateName = connection.prepareStatement(
                "UPDATE names SET firstName = ?, lastName = ? " +
                        "WHERE personID = ?" );

        // update address in table addresses
        sqlUpdateAddress = connection.prepareStatement(
                "UPDATE addresses SET address1 = ?, address2 = ?, " +
                        "city = ?, county = ?" +
                        "WHERE addressID = ?" );
/***************************************************/
        testsqlUpdateAddress = connection.prepareStatement(
                "UPDATE addresses SET address1 = ?, address2 = ?, " +
                        "city = ?, county = ?," +
                        "altaddress1 = ?, altaddress2 = ?, " +
                        "altcity = ?, altcounty = ?" +
                        "WHERE addressID = ?" );
/***************************************************/
        // update phone number in table phoneNumbers
        sqlUpdatePhone = connection.prepareStatement(
                "UPDATE phoneNumbers SET phoneNumber = ? " +
                        "altphoneNumber = ?" +
                        "WHERE phoneID = ?" );
        /************************************************/
        testsqlUpdatePhone = connection.prepareStatement(
                "UPDATE phoneNumbers SET phoneNumber = ?, " +
                        "altphoneNumber = ?" +
                        "WHERE phoneID = ?" );

        // update email in table emailAddresses
        sqlUpdateEmail = connection.prepareStatement(
                "UPDATE emailAddresses SET emailAddress = ? " +
                        "altemailAddress = ?" +
                        "WHERE emailID = ?" );

        // Delete row from table names. This must be executed
        // after sqlDeleteAddress, sqlDeletePhone and
        // sqlDeleteEmail, because of referential integrity.
        sqlDeleteName = connection.prepareStatement(
                "DELETE FROM names WHERE personID = ?" );

        // delete address from table addresses
        sqlDeleteAddress = connection.prepareStatement(
                "DELETE FROM addresses WHERE personID = ?" );

        // delete phone number from table phoneNumbers
        sqlDeletePhone = connection.prepareStatement(
                "DELETE FROM phoneNumbers WHERE personID = ?" );

        // delete email address from table emailAddresses
        sqlDeleteEmail = connection.prepareStatement(
                "DELETE FROM emailAddresses WHERE personID = ?" );
    }  // end CloudscapeDataAccess constructor

    // Obtain a connection to addressbook database. Method may
    // may throw ClassNotFoundException or SQLException. If so,
    // exception is passed via this class's constructor back to
    // the AddressBook application so the application can display
    // an error message and terminate.
    private void connect() throws Exception
    {
        // Cloudscape database driver class name
        String driver = "com.mysql.jdbc.Driver";

        // URL to connect to addressbook database
        String url = "jdbc:mysql://localhost:3306/addressbook";

        // load database driver class
        Class.forName( driver );

        // connect to database
        connection = DriverManager.getConnection( url ,"root","sufi6321656");

        // Require manual commit for transactions. This enables
        // the program to rollback transactions that do not
        // complete and commit transactions that complete properly.
        connection.setAutoCommit( false );
    }
   /*
   private void connect() throws Exception
   {
      String database = "addressbook";
      String driver = "com.mysql.jdbc.Driver";
      String user = "root";
      String url = "jdbc:mysql://localhost:3306/";
      try {
         Class.forName(driver);
         this.connection = DriverManager.getConnection(url, user, "qsdmark");
         this.connection.setAutoCommit(false);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }*/

    // Locate specified person. Method returns AddressBookEntry
    // containing information.
    public List<AddressBookEntry> findPerson( String lastName )
    {
        // AddressBookEntry[] array = new AddressBookEntry[10];
        List<AddressBookEntry> arrayList = new ArrayList<AddressBookEntry>();

        // List<Person> = new Person
        try {
            // set query parameter and execute query
            testsqlFind.setString( 1, lastName );
            ResultSet resultSet = testsqlFind.executeQuery();
            // sqltestFind.setString( 1, lastName );
            // ResultSet resultSet = sqltestFind.executeQuery();

            // if no records found, return immediately
            // if ( !resultSet.next() )
            //  return null;

            while(resultSet.next()) {
                // create new AddressBookEntry
                AddressBookEntry person = new AddressBookEntry(
                        resultSet.getInt(1));

                // set AddressBookEntry properties
                person.setFirstName(resultSet.getString(2));
                person.setLastName(resultSet.getString(3));

                person.setAddressID(resultSet.getInt(4));
                person.setAddress1(resultSet.getString(5));
                person.setAddress2(resultSet.getString(6));
                person.setCity(resultSet.getString(7));
                person.setState(resultSet.getString(8));
                /***************************/
                person.setAddress12(resultSet.getString(9));
                person.setAddress22(resultSet.getString(10));
                person.setCity2(resultSet.getString(11));
                person.setState2(resultSet.getString(12));
                /***************************/
                person.setPhoneID(resultSet.getInt(13));
                person.setPhoneNumber(resultSet.getString(14));
                person.setPhoneNumber2(resultSet.getString(15));


                person.setEmailAddress(resultSet.getString(16));
                person.setEmailAddress2(resultSet.getString(17));
                //person.setEmailID(resultSet.getInt(18));
                arrayList.add(person);
                // return AddressBookEntry
            }
            return arrayList;
        }

        // catch SQLException
        catch ( SQLException sqlException ) {
            return null;
        }
    }  // end method findPerson

    // Update an entry. Method returns boolean indicating
    // success or failure.
    public boolean savePerson( AddressBookEntry person )
            throws DataAccessException
    {
        // update person in database
        try {
            int result;

            /**************************************************************/
            /**************************************************************/
            // update names table
            sqlUpdateName.setString( 1, person.getFirstName() );
            sqlUpdateName.setString( 2, person.getLastName() );
            sqlUpdateName.setInt( 3, person.getPersonID() );
            result = sqlUpdateName.executeUpdate();

            // if update fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // update addresses table
            testsqlUpdateAddress.setString( 1, person.getAddress1() );
            testsqlUpdateAddress.setString( 2, person.getAddress2() );
            testsqlUpdateAddress.setString( 3, person.getCity() );
            testsqlUpdateAddress.setString( 4, person.getState() );
            /***************************************************/
            testsqlUpdateAddress.setString( 5, person.getAddress12() );
            testsqlUpdateAddress.setString( 6, person.getAddress22() );
            testsqlUpdateAddress.setString( 7, person.getCity2() );
            testsqlUpdateAddress.setString( 8, person.getState2() );
            /***************************************************/
            testsqlUpdateAddress.setInt( 9, person.getAddressID() );
            /**************************************************/
            //testsqlUpdateAddress.setString( 8, person.getState2() );
            result = testsqlUpdateAddress.executeUpdate();
            /**************************************************/


            // if update fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // update phoneNumbers table
            testsqlUpdatePhone.setString( 1, person.getPhoneNumber() );
            testsqlUpdatePhone.setString(2, person.getPhoneNumber2());
            testsqlUpdatePhone.setInt( 3, person.getPhoneID() );
            result = testsqlUpdatePhone.executeUpdate();

            // if update fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // update emailAddresses table
            testsqlUpdateEmail.setString( 1, person.getEmailAddress());
            testsqlUpdateEmail.setString( 2, person.getEmailAddress2());
            testsqlUpdateEmail.setInt( 3, person.getEmailID() );
            result = testsqlUpdateEmail.executeUpdate();

            // if update fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            connection.commit();   // commit update
            return true;           // update successful
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {

            // rollback transaction
            try {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                throw new DataAccessException( exception );
            }
        }
    }  // end method savePerson

    // Insert new entry. Method returns boolean indicating
    // success or failure.
    /*****************************BEANS BEANS THE MORE YOU EAT ************************/
    /************* the bean passed in here !***********/
    public boolean newPerson( AddressBookEntry person )
            throws DataAccessException
    {
        // insert person in database
        try {
            int result;

            // insert first and last name in names table
            sqlInsertName.setString( 1, person.getFirstName() );
            sqlInsertName.setString( 2, person.getLastName() );
            result = sqlInsertName.executeUpdate();

            // if insert fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback insert
                return false;          // insert unsuccessful
            }


            /***** INSERTING A NEW PERSON INTO THE DATABASE ****/
            // determine new personID
            ResultSet resultPersonID = sqlPersonID.executeQuery();

            if ( resultPersonID.next() ) {
                int personID =  resultPersonID.getInt( 1 );

                /*****************************************/
//            sqlInsertAddress.setInt( 1, personID );
//            sqlInsertAddress.setString( 2,
//                    person.getAddress1() );
//            sqlInsertAddress.setString( 3,
//                    person.getAddress2() );
//            sqlInsertAddress.setString( 4,
//                    person.getCity() );
//            sqlInsertAddress.setString( 5,
//                    person.getState() );
//            result = sqlInsertAddress.executeUpdate();
/**********************************************************/
                testsqlInsertAddress.setInt( 1, personID );
                testsqlInsertAddress.setString( 2,
                        person.getAddress1() );
                testsqlInsertAddress.setString( 3,
                        person.getAddress2() );
                testsqlInsertAddress.setString( 4,
                        person.getCity() );
                testsqlInsertAddress.setString( 5,
                        person.getState() );
                testsqlInsertAddress.setString( 6,
                        person.getAddress12() );
                testsqlInsertAddress.setString( 7,
                        person.getAddress22() );
                testsqlInsertAddress.setString( 8,
                        person.getCity2() );
                testsqlInsertAddress.setString( 9,
                        person.getState2() );
                result = testsqlInsertAddress.executeUpdate();

                // if insert fails, rollback and discontinue
                if ( result == 0 ) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }

                // insert phone number in phoneNumbers table
                sqlInsertPhone.setInt( 1, personID );
                sqlInsertPhone.setString( 2,
                        person.getPhoneNumber() );
                sqlInsertPhone.setString( 3,
                        person.getPhoneNumber2() );
                result = sqlInsertPhone.executeUpdate();

                // if insert fails, rollback and discontinue
                if ( result == 0 ) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }

                // insert email address in emailAddresses table
                sqlInsertEmail.setInt( 1, personID );
                sqlInsertEmail.setString( 2,
                        person.getEmailAddress() );
                sqlInsertEmail.setString( 3,
                        person.getEmailAddress2() );
                result = sqlInsertEmail.executeUpdate();

                // if insert fails, rollback and discontinue
                if ( result == 0 ) {
                    connection.rollback(); // rollback insert
                    return false;          // insert unsuccessful
                }

                connection.commit();   // commit insert
                return true;           // insert successful
            }

            else
                return false;
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                throw new DataAccessException( exception );
            }
        }
    }  // end method newPerson

    // Delete an entry. Method returns boolean indicating
    // success or failure.
    public boolean deletePerson( AddressBookEntry person )
            throws DataAccessException
    {
        // delete a person from database
        try {
            int result;

            // delete address from addresses table
            sqlDeleteAddress.setInt( 1, person.getPersonID() );
            result = sqlDeleteAddress.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            // delete phone number from phoneNumbers table
            sqlDeletePhone.setInt( 1, person.getPersonID() );
            result = sqlDeletePhone.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            // delete email address from emailAddresses table
            sqlDeleteEmail.setInt( 1, person.getPersonID() );
            result = sqlDeleteEmail.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            // delete name from names table
            sqlDeleteName.setInt( 1, person.getPersonID() );
            result = sqlDeleteName.executeUpdate();

            // if delete fails, rollback and discontinue
            if ( result == 0 ) {
                connection.rollback(); // rollback delete
                return false;          // delete unsuccessful
            }

            connection.commit();   // commit delete
            return true;           // delete successful
        }  // end try

        // detect problems updating database
        catch ( SQLException sqlException ) {
            // rollback transaction
            try {
                connection.rollback(); // rollback update
                return false;          // update unsuccessful
            }

            // handle exception rolling back transaction
            catch ( SQLException exception ) {
                throw new DataAccessException( exception );
            }
        }
    }  // end method deletePerson

    // method to close statements and database connection
    public void close()
    {
        // close database connection
        try {
            sqlFind.close();
            sqlPersonID.close();
            sqlInsertName.close();
            sqlInsertAddress.close();
            sqlInsertPhone.close();
            sqlInsertEmail.close();
            sqlUpdateName.close();
            sqlUpdateAddress.close();
            sqlUpdatePhone.close();
            sqlUpdateEmail.close();
            sqlDeleteName.close();
            sqlDeleteAddress.close();
            sqlDeletePhone.close();
            sqlDeleteEmail.close();
            connection.close();
        }  // end try

        // detect problems closing statements and connection
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    }  // end method close

    // Method to clean up database connection. Provided in case
    // CloudscapeDataAccess object is garbage collected.
    protected void finalize()
    {
        close();
    }
}  // end class CloudscapeDataAccess