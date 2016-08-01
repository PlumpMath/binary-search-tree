import java.util.ArrayList;

/* Relation represents a binary relation in the form (x, y) where x represents
 * a country and y represents the language in that country. */
public interface Relation <X extends Comparable<X>, Y extends Comparable<Y>> {
	
	/* Returns true if the relation contains
	 * a specific pair of objects. In this case
	 * it is going to be (country, language). */
	public boolean contains(X elem1, Y elem2);
	
	/* Prints all Y values containing X. */
	public ArrayList<Y> determineViaX(X elem1);
	
	/* Prints all X values containing Y. */
	public ArrayList<X> determineViaY(Y elem2);
	
	/* Empties the DT. */
	public void empty();
	
	/* Inserts a pair of elements in the DT. */
	public void insert(X elem1, Y elem2);
	
	/* Deletes a pair of elements from the DT. */
	public void delete(X elem1, Y elem2);
	
	/* Deletes all pairs having a specific first element. */
	public void deleteViaX(X elem1);
	
	/* Deletes all pairs having a specific second element. */
	public void deleteViaY(Y elem2);
	
	/* Prints the contents of the DT. */
	public void print();
	
}
