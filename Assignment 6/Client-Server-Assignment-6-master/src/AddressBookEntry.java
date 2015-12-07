// JavaBean to represent one address book entry.

public class AddressBookEntry {
   private String firstName = "";
   private String lastName = "";
   private String mms = "";
   private String address1 = "";
   private String address2 = "";
   private String city = "";
   private String state = "";
   /**************************/
   private String address12 = "";
   private String address22 = "";
   private String city2 = "";
   private String state2 = "";
   /**************************/
   private String zipcode = "";
   private String phoneNumber2 = "";

   private String emailAddress2 = "";
   private String phoneNumber = "";

   private String emailAddress = "";

   private int personID;
   private int addressID;
   private int phoneID;
   private int emailID;

   public String getPhoneNumber2() {
      return phoneNumber2;
   }

   public void setPhoneNumber2(String phoneNumber2) {
      this.phoneNumber2 = phoneNumber2;
   }

   public String getEmailAddress2() {
      return emailAddress2;
   }

   public void setEmailAddress2(String emailAddress2) {
      this.emailAddress2 = emailAddress2;
   }



   public String getAddress22() {
      return address22;
   }

   public void setAddress22(String address22) {
      this.address22 = address22;
   }

   public String getAddress12() {
      return address12;
   }

   public void setAddress12(String address12) {
      this.address12 = address12;
   }

   public String getCity2() {
      return city2;
   }

   public void setCity2(String city2) {
      this.city2 = city2;
   }

   public String getState2() {
      return state2;
   }

   public void setState2(String state2) {
      this.state2 = state2;
   }


   
   // empty constructor
   public AddressBookEntry()
   {
   }

   // set person's id
   public AddressBookEntry( int id )
   {
      personID = id;
   }
   
   // set person's first name
   public void setFirstName( String first )
   {
      firstName = first;
   }
   
   // get person's first name
   public String getFirstName()
   {
      return firstName;
   }
   
   // set person's last name
   public void setLastName( String last )
   {
      lastName = last;
   }
   
   // get person's last name
   public String getLastName()
   {
      return lastName;
   }
//////////////////
public void setmms( String ms )
{
   mms = ms;
}

   // get person's last name
   public String getmms()
   {
      return mms;
   }

   // set first line of person's address
   public void setAddress1( String firstLine )
   {
      address1 = firstLine;
   }
   
   // get first line of person's address
   public String getAddress1()
   {
      return address1;
   }
   
   // set second line of person's address
   public void setAddress2( String secondLine )
   {
      address2 = secondLine;
   }
   
   // get second line of person's address
   public String getAddress2()
   {
      return address2;
   }
   
   // set city in which person lives
   public void setCity( String personCity )
   {
      city = personCity;
   }
   
   // get city in which person lives
   public String getCity()
   {
      return city;
   }
   
   // set state in which person lives
   public void setState( String personState )
   {
      state = personState;
   }

   // get state in which person lives
   public String getState()
   {
      return state;
   }

   /****************************************************************/
   // set state in which person lives
   /******************************************************/
   // set person's zip code
   public void setZipcode( String zip )
   {
      zipcode = zip;
   }
   
   // get person's zip code
   public String getZipcode()
   {
      return zipcode;
   }
   
   // set person's phone number
   public void setPhoneNumber( String number )
   {
      phoneNumber = number;
   }
   
   // get person's phone number 
   public String getPhoneNumber()
   {
      return phoneNumber;
   }

   // set person's email address
   public void setEmailAddress( String email )
   {
      emailAddress = email;
   }
   
   // get person's email address
   public String getEmailAddress()
   {
      return emailAddress;
   }
   
   // get person's ID
   public int getPersonID()
   {
      return personID;
   }
   
   // set person's addressID
   public void setAddressID( int id )
   {
      addressID = id;
   }
   
   // get person's addressID
   public int getAddressID()
   {
      return addressID;
   }
   
   // set person's phoneID
   public void setPhoneID( int id )
   {
      phoneID = id;
   }
   
   // get person's phoneID
   public int getPhoneID()
   {
      return phoneID;
   }
   
   // set person's emailID
   public void setEmailID( int id )
   {
      emailID = id;
   }
   
   // get person's emailID
   public int getEmailID()
   {
      return emailID;
   }
}  // end class AddressBookEntry


/**************************************************************************
 * (C) Copyright 2001 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
