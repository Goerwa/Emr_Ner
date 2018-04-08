package P2;

public class Person {
	
	// Abstraction function:
    // represent a person who has its name 
    //
    // Representation invariant:
    // null
    //
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients. 
    //
	private String name;
   
    /**
     * initialize the name
     *
     * @param name
     */
    public Person(String name) {
        this.name = name;
    }
    
    /**
     * get the name of a person
     *
     * @param null
     */
    public String getName(){
    	return name;
    }
}
