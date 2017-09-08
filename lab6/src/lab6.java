import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by tgit on 08/09/17.
 */
public class lab6<T> {
    public static void main(String str[]) throws IOException {
        Scanner read = new Scanner(System.in);
        System.out.println("1. The   number   of   knights   ");
        int numOfKnights = Integer.parseInt(read.nextLine().split(" ")[0]);
        System.out.println("2. The   total   number   of   iterations ");
        int numOfIterations = Integer.parseInt(read.nextLine().split(" ")[0]);
        System.out.println("3. Coordinates   of   queen   x   and   y   ");
        String co[] = read.nextLine().split(" ");
        Coordinate queen = new Coordinate(Integer.parseInt(co[0]), Integer.parseInt(co[1]));
        knight knightsArray[] = new knight[numOfKnights];
        int flag=0;

        for (int i = 0; i < numOfKnights; i++) {
            // read m lines for m knights

            int k = i + 1;
            knightsArray[i] = new knight();
            FileInputStream fin = new FileInputStream("/Users/tgit/Downloads/Lab6/TestCase/Input/" + k + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            knightsArray[i].name = br.readLine();
            co = br.readLine().split(" ");
            knightsArray[i].c = new Coordinate(Integer.parseInt(co[0]), Integer.parseInt(co[1]));
            knightsArray[i].sizeOfmgkBox = Integer.parseInt(br.readLine());
            knightsArray[i].mgkbox = new Stack();
//            try {


                for (int g = 0; g < knightsArray[i].sizeOfmgkBox; g++) {
                    String[] input = br.readLine().split(" ");
                    if (input[0].compareTo("Integer") == 0) {
                        knightsArray[i].mgkbox.push(Integer.parseInt(input[1]));
//                        System.out.println("added int");
                    } else if (input[0].compareTo("String") == 0) {
//                        System.out.println("added str");
                        knightsArray[i].mgkbox.push(input[1]);
                    } else if (input[0].compareTo("Coordinate") == 0) {
                        knightsArray[i].mgkbox.push(new Coordinate(Integer.parseInt(input[1]), Integer.parseInt(input[2])));
//                        System.out.println("added coordinate");
                    }
                    else if (input[0].compareTo("Float") == 0) {
                        knightsArray[i].mgkbox.push(Float.parseFloat(input[1]));
//                        System.out.println("added coordinate");
                    }
//                    else
//                        throw new InvalidTypeException("");
                }
//            }
//            catch (InvalidTypeException e){
//                System.out.println("InvalidType");
//            }

        }

        Arrays.sort(knightsArray);
        int count = numOfKnights, programEnd = 0;
        Object pop=new Object();

        for (int j = 0; j < numOfIterations; j++) {
            for (int k = 0; k < numOfKnights; k++) {
                flag=0;
                if (knightsArray[k].isDead == false) {
                    System.out.println(j + 1 + " " + knightsArray[k].name + " " + knightsArray[k].c.x + "," + knightsArray[k].c.y);
//todo empty stack check

                    try {
                        if (knightsArray[k].mgkbox.isEmpty() == false) {
//                        System.out.println("LOG: stack is not empty");
                            knightsArray[k].popped = knightsArray[k].mgkbox.pop();
                            pop =knightsArray[k].popped;
                            try {
                                if (knightsArray[k].popped instanceof Coordinate) {
//                                System.out.println("LOG: popped item is coordinate ");
                                    knightsArray[k].c.setCoordinates(((Coordinate) knightsArray[k].popped).x, ((Coordinate) knightsArray[k].popped).y);
//                                System.out.println("LOG: popped coordinates are "+((Coordinate) knightsArray[k].popped).x+" "+((Coordinate) knightsArray[k].popped).y);
//                                System.out.println("LOG: updated coordinates are "+knightsArray[k].c.x+" "+knightsArray[k].c.y);
                                    for (int m = 0; m < numOfKnights; m++) {
                                        try {
                                            if (knightsArray[m].c.equals(knightsArray[k].c) && (!knightsArray[m].equals(knightsArray[k]))) {
//                                            System.out.println("LOG: overlap");
                                                count--;
                                                knightsArray[m].isDead = true;
                                                throw new OverlapException("");
                                            }
                                        } catch (OverlapException e) {
                                            flag = 1;
                                            System.out.println("OverlapException:   Knights   Overlap   Exception " + knightsArray[m].name);

                                        }
                                    }
                                    try {

                                        if (knightsArray[k].c.equals(queen)) {
                                            programEnd = 1;
                                            throw new QueenFoundException("");
//                        break;
                                        }
                                    } catch (QueenFoundException e) {
                                        flag = 1;
                                        System.out.println("QueenFoundException: Queen   has   been   Found.   Abort!");

                                    }
                                } else

                                    throw new NonCoordinateException("");
                            } catch (NonCoordinateException e) {
                                flag = 1;
                                System.out.println("NonCoordinateException: Not a coordinate" + "Exception " + knightsArray[k].popped);
                            }
                        } else if (knightsArray[k].mgkbox.isEmpty() == true) {
                            count--;
                            knightsArray[k].isDead = true;
                            // ongrid knight count--
                            // remove knight from grid
                            throw new StackEmptyException("");
                        }
                    } catch (StackEmptyException e) {
                        flag = 1;
                        System.out.println("StackEmptyException:   Stack   Empty   exception");

                    }
//                    System.out.println("popped class " + pop.getClass());
//                    System.out.println("flag valu "+ flag);
                    if ((knightsArray[k].popped instanceof Coordinate) && (flag == 0)) {
                        System.out.println("No exception " + ((Coordinate) knightsArray[k].popped).x + " " + ((Coordinate) knightsArray[k].popped).y);
                    }
                }

            }
            if (programEnd == 1)
                break;
        }
//todo no exception

    }

}

class knight<T> implements Comparable<knight> {
    String name;
    Coordinate c;
    int sizeOfmgkBox;
    Stack mgkbox;
    T popped;
    boolean isDead = false;

    @Override
    public int compareTo(knight o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj) {
        knight x=(knight) obj;
        if (this.name.compareTo(x.name)==0)
            return true;
        return false;
    }
}

class Coordinate {
    int x;
    int y;

    public Coordinate(int a, int b) {
        this.x = a;
        this.y = b;
    }

    public void setCoordinates(int a, int b) {
        this.x = a;
        this.y = b;
    }

    @Override
    public boolean equals(Object obj) {
        Coordinate c =(Coordinate)obj;
        if((this.x==c.x)&&(this.y==c.y)){
            return true;
        }
        else return false;
    }
}

class NonCoordinateException extends Exception {
    public NonCoordinateException(String message) {
        super(message);
    }

}

class StackEmptyException extends Exception {
    public StackEmptyException(String message) {
        super(message);
    }
}

class OverlapException extends Exception {
    public OverlapException(String message) {
        super(message);
    }
}

class QueenFoundException extends Exception {
    public QueenFoundException(String message) {
        super(message);
    }
}
class InvalidTypeException extends Exception {
    public InvalidTypeException(String message) {
        super(message);
    }
}

class NoException extends Exception {
    public NoException(String message) {
        super(message);
    }
}