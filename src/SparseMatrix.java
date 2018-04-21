import java.util.Scanner;

public class SparseMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Sparse Matrix setup");
		System.out.print("No. of rows:");
		int row = in.nextInt();
		System.out.print("No. of columns:");
		int col = in.nextInt();
		System.out.println();
		String tmp = in.nextLine();
		MultiList list = new MultiList(col, row);
		System.out.println(row + "x" + col + " sparse matrix is created.");
		String op = "";
		while (!op.equalsIgnoreCase("stop")) {
			System.out.println();
			System.out.print(">");
			op = in.nextLine();
			String[] ops = op.split(" ");
			switch (ops[0]) {
			case "insert":
				if (list.insert(Integer.valueOf(ops[3]), Integer.valueOf(ops[2]), Integer.valueOf(ops[1])))
					System.out.printf("Inserted %s to cell(%s,%s).\n", ops[1], ops[2], ops[3]);
				else
					System.out.printf("Invalid Input: Position out of Matrix\n");
				break;
			case "row":
				Node rowHead = list.findRow(Integer.valueOf(ops[1]));
				int count1 = 0;
				while (rowHead != null && rowHead.next_x != null) {
					for (int i = 1; i < rowHead.next_x.x - rowHead.x; i++) {
						System.out.print(" " + 0);
						count1++;
					}
					System.out.print(" " + rowHead.next_x.value);
					count1++;
					rowHead = rowHead.next_x;
				}
				for (; count1 < list.width; count1++)
					System.out.print(" " + 0);
				System.out.println();
				break;
			case "col":
				Node colHead = list.findCol(Integer.valueOf(ops[1]));
				int count2 = 0;
				while (colHead != null && colHead.next_y != null) {
					for (int i = 1; i < colHead.next_y.y - colHead.y; i++) {
						System.out.print(" " + 0);
						count2++;
					}
					System.out.print(" " + colHead.next_y.value);
					count2++;
					colHead = colHead.next_y;
				}
				for (; count2 < list.height; count2++)
					System.out.print(" " + 0);
				System.out.println();
				break;
			case "delete":
				if (list.delete(Integer.valueOf(ops[2]), Integer.valueOf(ops[1])))
					System.out.printf("delete cell(%s,%s).\n", ops[1], ops[2]);
				else
					System.out.printf("Invalid Input: No such element\n");
				break;
			case "get":
				Node curr = list.find(Integer.valueOf(ops[2]), Integer.valueOf(ops[1]));
				if (curr != null)
					System.out.printf("The value of cell(%s,%s) is %d\n", ops[1], ops[2], curr.value);
				else
					System.out.printf("The value of cell(%s,%s) is %d\n", ops[1], ops[2], 0);
				break;
			default:
				break;
			}
		}
		System.out.print("Thank you for using this progam!");
	}

}

class Node {
	Node next_x, next_y;
	int x, y;
	int value;

	Node(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
}

class MultiList {
	Node root;
	int width, height;

	MultiList(int w, int h) {
		this.root = new Node(0, 0, 0);
		this.width = w;
		this.height = h;
	}

	boolean insert(int x, int y, int v) {
		if (x > width || y > height)
			return false;
		Node curr = new Node(x, y, v);
		Node rowFinder = root;
		while (true) {
			if (rowFinder.next_y == null) {
				rowFinder.next_y = new Node(0, y, 0);
				rowFinder = rowFinder.next_y;
				break;
			}
			if (rowFinder.next_y.y == y) {
				rowFinder = rowFinder.next_y;
				break;
			}
			if (rowFinder.next_y.y > y) {
				Node tmp = rowFinder.next_y;
				rowFinder.next_y = new Node(0, y, 0);
				rowFinder = rowFinder.next_y;
				rowFinder.next_y = tmp;
				break;
			}
			rowFinder = rowFinder.next_y;
		}
		while (true) {
			if (rowFinder.next_x == null) {
				rowFinder.next_x = curr;
				rowFinder = rowFinder.next_x;
				break;
			}
			if (rowFinder.x == x) {
				rowFinder = rowFinder.next_x;
				break;
			}
			if (rowFinder.next_x.x > x) {
				Node tmp = rowFinder.next_x;
				rowFinder.next_x = curr;
				rowFinder = rowFinder.next_x;
				rowFinder.next_x = tmp;
				break;
			}
			rowFinder = rowFinder.next_x;
		}

		Node colFinder = root;
		while (true) {
			if (colFinder.next_x == null) {
				colFinder.next_x = new Node(x, 0, 0);
				colFinder = colFinder.next_x;
				break;
			}
			if (colFinder.next_x.x == x) {
				colFinder = colFinder.next_x;
				break;
			}
			if (colFinder.next_x.x > x) {
				Node tmp = colFinder.next_x;
				colFinder.next_x = new Node(x, 0, 0);
				colFinder = colFinder.next_x;
				colFinder.next_x = tmp;
				break;
			}
			colFinder = colFinder.next_x;
		}
		while (true) {
			if (colFinder.next_y == null) {
				colFinder.next_y = curr;
				colFinder = colFinder.next_y;
				break;
			}
			if (colFinder.y == y) {
				colFinder = colFinder.next_y;
				break;
			}
			if (colFinder.next_y.y > y) {
				Node tmp = colFinder.next_y;
				colFinder.next_y = curr;
				colFinder = colFinder.next_y;
				colFinder.next_y = tmp;
				break;
			}
			colFinder = colFinder.next_y;

		}

		return true;
	}

	boolean delete(int x, int y) {
		Node rowFinder = root;
		while (true) {
			if (rowFinder.next_y == null)
				return false;
			if (rowFinder.next_y.y > y)
				return false;
			if (rowFinder.next_y.y == y) {
				rowFinder = rowFinder.next_y;
				break;
			}
			rowFinder = rowFinder.next_y;
		}
		while (true) {
			if (rowFinder.next_x == null)
				return false;
			if (rowFinder.next_x.x > x)
				return false;
			if (rowFinder.next_x.x == x) {
				rowFinder.next_x = rowFinder.next_x.next_x;
				break;
			}
			rowFinder = rowFinder.next_x;
		}

		Node colFinder = root;
		while (colFinder.x == x) {
			colFinder = colFinder.next_x;
		}
		while (colFinder.next_y.y == y) {
			colFinder = colFinder.next_y;
		}
		colFinder.next_y = colFinder.next_y.next_y;

		return true;
	}

	Node find(int x, int y) {
		Node rowFinder = root;
		while (true) {
			if (rowFinder.next_y == null)
				return null;
			if (rowFinder.next_y.y > y)
				return null;
			if (rowFinder.next_y.y == y) {
				rowFinder = rowFinder.next_y;
				break;
			}
			rowFinder = rowFinder.next_y;
		}
		while (true) {
			if (rowFinder.next_x == null)
				return null;
			if (rowFinder.next_x.x > x)
				return null;
			if (rowFinder.next_x.x == x) {
				return rowFinder.next_x;
			}
			rowFinder = rowFinder.next_x;
		}
	}

	Node findRow(int y) {
		Node rowFinder = root;
		while (true) {
			if (rowFinder.next_y == null)
				return null;
			if (rowFinder.next_y.y > y)
				return null;
			if (rowFinder.next_y.y == y) {
				rowFinder = rowFinder.next_y;
				break;
			}
			rowFinder = rowFinder.next_y;
		}
		return rowFinder;
	}

	Node findCol(int x) {
		Node colFinder = root;
		while (true) {
			if (colFinder.next_x == null)
				return null;
			if (colFinder.next_x.x > x)
				return null;
			if (colFinder.next_x.x == x) {
				colFinder = colFinder.next_x;
				break;
			}
			colFinder = colFinder.next_x;
		}
		return colFinder;
	}
}
