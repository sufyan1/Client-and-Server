// A subclass of JInternalFrame customized to display and
// an AddressBookEntry or set an AddressBookEntry's properties
// based on the current data in the UI.

// Java core packages
import java.util.*;
import java.awt.*;

// Java extension packages
import javax.swing.*;

public class AddressBookEntryFrame extends JInternalFrame {

   // HashMap to store JTextField references for quick access
   private HashMap fields;

   // current AddressBookEntry set by AddressBook application
   private AddressBookEntry person;

   // panels to organize GUI
   private JPanel leftPanel, rightPanel;

   // static integers used to determine new window positions  
   // for cascading windows
   private static int xOffset = 0, yOffset = 0;

   // static Strings that represent name of each text field.
   // These are placed on JLabels and used as keys in 
   // HashMap fields.
   private static final String FIRST_NAME = "First Name",
           LAST_NAME = "Last Name", ADDRESS1 = "Primary Address 1",
           ADDRESS2 = "Primary Address 2", CITY = "Primary City", STATE = "Primary County",
   /**********/
   ALTADDRESS1 = "Alternate Address 1", ALTADDRESS2 = "Alternative Address 2",
           ALTCITY = "Alternative city", ALTSTATE = "Alternative County",
   /*************/
   ZIPCODE = "Zipcode", PHONE = "Phone", ALTPHONE = "Alternative phone",
           EMAIL = "Email", ALTEMAIL = "Alternative Email";

   // construct GUI
   public AddressBookEntryFrame()
   {
      super( "Address Book Entry", true, true );

      fields = new HashMap();

      leftPanel = new JPanel();
      leftPanel.setLayout( new GridLayout( 9, 1, 0, 5 ) );
      rightPanel = new JPanel();
      rightPanel.setLayout( new GridLayout( 9, 1, 0, 5 ) );

      createRow( FIRST_NAME );
      createRow( LAST_NAME );
      createRow( ADDRESS1 );
      createRow( ADDRESS2 );
      createRow( CITY );
      createRow( STATE );
      /*********************/
      createRow( ALTADDRESS1 );
      createRow( ALTADDRESS2 );
      createRow( ALTCITY );
      createRow( ALTSTATE );
      /*****************/
      // createRow( ZIPCODE );
      createRow( PHONE );
      createRow( ALTPHONE );
      createRow( EMAIL );
      createRow( ALTEMAIL );

      Container container = getContentPane();
      container.add( leftPanel, BorderLayout.WEST );
      container.add( rightPanel, BorderLayout.CENTER );

      setBounds( xOffset, yOffset, 300, 300 );
      xOffset = ( xOffset + 30 ) % 300;
      yOffset = ( yOffset + 30 ) % 300;
   }

   // set AddressBookEntry then use its properties to
   // place data in each JTextField
   public void setAddressBookEntry(AddressBookEntry entry ) {
      person = entry;

      setField(FIRST_NAME, person.getFirstName());
      setField(LAST_NAME, person.getLastName() );
      setField(ADDRESS1, person.getAddress1() );
      setField(ADDRESS2, person.getAddress2() );
      setField( CITY, person.getCity() );
      setField( STATE, person.getState() );
      /********************************/
      setField( ALTADDRESS1, person.getAddress12() );
      setField( ALTADDRESS2, person.getAddress22() );
      setField( ALTCITY, person.getCity2() );
      setField( ALTSTATE, person.getState2() );
      /********************************/
      setField( PHONE, person.getPhoneNumber() );
      setField( ALTPHONE, person.getPhoneNumber2() );
      setField( EMAIL, person.getEmailAddress() );
      setField( ALTEMAIL, person.getEmailAddress2() );
   }

   // store AddressBookEntry data from GUI and return
   // AddressBookEntry
   public AddressBookEntry getAddressBookEntry()
   {
      person.setFirstName( getField( FIRST_NAME ) );
      person.setLastName( getField( LAST_NAME ) );
      person.setAddress1( getField( ADDRESS1 ) );
      person.setAddress2( getField( ADDRESS2 ) );
      person.setCity( getField( CITY ) );
      person.setState( getField( STATE ) );
      person.setAddress12( getField( ALTADDRESS1 ) );
      person.setAddress22( getField( ALTADDRESS2 ) );
      person.setCity2( getField( ALTCITY ) );
      person.setState2( getField( ALTSTATE ) );
      // person.setZipcode( getField( ZIPCODE ) );
      person.setPhoneNumber( getField( PHONE ) );
      person.setEmailAddress( getField( EMAIL ) );
      person.setPhoneNumber2( getField( ALTPHONE ) );
      person.setEmailAddress2( getField( ALTEMAIL ) );
      return person;
   }

   // set text in JTextField by specifying field's
   // name and value
   private void setField( String fieldName, String value )
   {
      JTextField field =
              ( JTextField ) fields.get( fieldName );

      field.setText( value );
   }

   // get text in JTextField by specifying field's name
   private String getField( String fieldName )
   {
      JTextField field =
              ( JTextField ) fields.get( fieldName );

      return field.getText();
   }

   // utility method used by constructor to create one row in
   // GUI containing JLabel and JTextField
   private void createRow( String name )
   {
      JLabel label = new JLabel( name, SwingConstants.RIGHT );
      label.setBorder(
              BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
      leftPanel.add( label );

      JTextField field = new JTextField( 30 );
      rightPanel.add( field );

      fields.put( name, field );
   }
}  // end class AddressBookEntryFrame


