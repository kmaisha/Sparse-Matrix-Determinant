import java.util.*;
import java.util.LinkedList;

public class SparseMatrix implements SparseInterface{
	public static void main(String [] args){
		
	}
	
	/*
	 * Creates a linked list of MartixElements object
	 */
	LinkedList<MatrixElements> SM = new LinkedList<>();
	private int size;
	/*
	 *Constructor 
	 */
	public SparseMatrix(){
		this.size = 5;
	}
	
	//The values of the matrix is is set to 0
	public void clear(){
		SM.clear();
	}
	
	//Sets maximum size of the matrix.
    public void setSize(int size){
    	clear();
    	this.size = size;
    	
    }
    
    //Adds an element to the row and column passed as arguments
    public void addElement(int row, int col, int data){
    	//Checks for invalid validation entry point 
    	if (row > size || col > size || row < 0 || col < 0){
    		//Checks for valid data and throws an exception
    		throw new IndexOutOfBoundsException("Error Message: Invalid entry point.");		
    	}
    	else if (data == 0){
    		throw new IndexOutOfBoundsException("Error Message: Invalid entry point.");
    	}
    	else{
    		//Makes an object of MatrixElements type to add elements  
    		removeElement(row, col);
    		MatrixElements elements = new MatrixElements (row, col, data);
    		SM.add(elements);
    	}
    }

    //Removes an element to the row and column passed as arguments
    public void removeElement(int row, int col){
    	if (row > size || col > size || row < 0 || col < 0){
    			throw new IndexOutOfBoundsException("Error Message: Row/Column combination is out of bounds.");
    	}
    	
    	else{
    		//Traverse the list to remove a specific data value
    		for (int i = 0; i < SM.size(); i++){
    			MatrixElements values = SM.get(i);
    			if (values.getRow() == row && values.getCol() == col){
    				SM.remove(values);
    				break;
    			}
    		}
    	}
    }
    
    // Returns an element at the specified row and column
    public int getElement(int row, int col){
    	if (row > size || col > size || row < 0 || col < 0){
			throw new IndexOutOfBoundsException("Error Message: Row/Column combination is out of bounds.");
	
    	}
    	//else{
    
    		//Traverse the list to return a specific data value at specific position
    		for (int i = 0; i < SM.size(); i++){
    			MatrixElements values = SM.get(i);
    			if (values.getRow() == row && values.getCol() == col){
    				return values.getData();
    			}
    		}
    	//}
    	return 0;
    }
    
    //Calculates the determinant
    public int determinant(){
    	//Base cases for 1x1 and 2x2 matrix
    	if (this.size == 1){
    		return getElement(0, 0);
    	}
    	else if (SM.size() == 4){
    		int x = getElement(0, 0) * getElement(1, 1);
    		int y = getElement(0, 1) * getElement(1, 0);
    		int dete = x - y;
    		
    		return dete;
    	}
    	//Recursive case
    	else {
    		int det = 0;
    		for (int i = 0; i < this.size; i++){
    			//Formula to calculate the determinant 
    			det += Math.pow(-1, i) * getElement(0, i) * minor(0, i).determinant();
    		}
    		return det;
    	}
    }

    //Returns a new matrix which is the minor of the original 
    public SparseInterface minor(int row, int col){
    	//Creates a new linked list to copy elements of the original matrix for minor
    	SparseMatrix minorSM = new SparseMatrix();
    	minorSM.setSize(this.size - 1);
    	
    	for (int i = 0; i < SM.size(); i++){
    		MatrixElements SpM = SM.get(i);
    		/*
    		 * Conditions to check which row and column to ignore to create the minor matrix
    		 * Copies the elements in the new minor matrix by decrementing the row/column or
    		 * keeping them the same depending on the which row/column are being ignored 
    		 */
    		if (SpM.getRow() < row && SpM.getCol() < col){
    			minorSM.addElement(SpM.getRow(), SpM.getCol(), SpM.getData());
    		}
    		if (SpM.getRow() < row && SpM.getCol() > col){
    			minorSM.addElement(SpM.getRow(), SpM.getCol() -1, SpM.getData());
    		}
    		if (SpM.getRow() > row && SpM.getCol() < col){
    			minorSM.addElement(SpM.getRow() - 1, SpM.getCol(), SpM.getData());
    		}
    		if (SpM.getRow() > row && SpM.getCol() > col) {
    			minorSM.addElement(SpM.getRow() - 1, SpM.getCol() - 1, SpM.getData());
    		}
    		
    	}
    	return minorSM;
    }

   
    public String toString(){
    	String printMinor = "";
    	//Nested for-loop to extract the element of minor and its position on the minor matrix
		for (int r = 0; r < size; r++){
			for (int c = 0; c < size; c++){
				for (int d = 0; d < SM.size(); d++){
					MatrixElements extract = SM.get(d);
						if (extract.getRow() == r && extract.getCol() == c){
							printMinor += extract.getRow() + " " + extract.getCol() + " " + extract.getData() + "\n";
					}
				}
			}
		}
		return printMinor;
    } 

    public int getSize(){
		return size;	
    }

}
