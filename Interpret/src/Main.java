import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	static Helper<strVal> str = new Helper<>(); // Resizable array to save
												// Strings
	static Helper<intVal> in = new Helper<>(); // Resizable array to save
												// Integers;

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Output:\n========================================");
		File text = new File("prog1.zpm");
		/* Text can be any of these 10 program files. Or the user can run it from the console (File text = new File(System.in);)
		 * 
		 * 
		 * File text = new File("prog2.zpm");
		 * File text = new File("prog3.zpm");
		 * File text = new File("prog4.zpm");
		 * File text = new File("prog5.zpm");
		 * File text = new File("prog6.zpm");
		 * File text = new File("prog7.zpm");
		 * File text = new File("prog8.zpm");
		 * File text = new File("prog9.zpm");
		 * File text = new File("prog10.zpm");
		 */
		// Creating Scanner instance to read File in Java
		Scanner scan = new Scanner(text);

		int line = 0;
		boolean broke = false;
		long start= System.currentTimeMillis();
		while (scan.hasNext()) {
			if (scan.hasNext(";")) {
				scan.next();
			}

			line++;

			if (scan.hasNext("FOR")) {
				Helper<Integer> forEnds = new Helper<>();
				int count = 0;
				Helper<String> content = new Helper<>();

				do {
					if (scan.hasNext("FOR")) {
						scan.next();
						forEnds.add(scan.nextInt());
						if(scan.hasNext("FOR"))
							content.add("");
						if (count != 0)
							content.set(count - 1, content.get(count - 1) + " ~");
						count++;
					} else if (scan.hasNext("ENDFOR")) {
						count--;
						scan.next();
					} else {
						if (content.get(count - 1) == null){
							content.add(scan.next());
						}
						else
							content.set(count - 1, content.get(count - 1) + " " + scan.next());
					}
				} while (count > 0);
				
				Scanner scan1 = new Scanner(content.get(count));

				Helper<Integer> original = new Helper<>();
				Helper<String> rest = new Helper<>();
				boolean r = false;
				int place=0;

				for (int i = 0; i < forEnds.size; i++)
					original.add(forEnds.get(i));

				while (count != -1 && forEnds.get(0)!=0) {
					
					if(count>1 && forEnds.get(count-1)==1 && forEnds.get(count)==1){
						forEnds.set(count-2, forEnds.get(count-2)-1);
						forEnds.set(count-1, original.get(count-1));
						forEnds.set(count, original.get(count));
						count-=2;
						String assi=content.get(count);
						if(assi.contains("~")){
							rest.add(assi.substring(assi.indexOf('~')+1));
							place++;
							r=true;
						}
						scan1= new Scanner(content.get(count));
						continue;
					}
					if(r){
						r=false;
						Scanner scan2= new Scanner(rest.get(place-1));

						String name = scan2.next();
						int[] verify = exist(name);
						if (verify[0] == 0) { // Variable is an int
							if (scan2.hasNext("-=")) {
								if (scan2.hasNextInt())
									in.set(verify[1], new intVal(name, in.get(verify[1]).value - scan2.nextInt()));
								else {
									scan2.next();
									String s = scan2.next();
									int[] assist = exist(s);
									if (assist[0] == 0)
										in.set(verify[1],
												new intVal(name, in.get(verify[1]).value - in.get(assist[1]).value));
									else {
										broke = true;
										System.out.println("RUNTIME ERROR 1 ON LINE " + line);
										break;
									}
								}
							}
							if (scan2.hasNext("\\+=")) {
								scan2.next();
								if (scan2.hasNextInt()){
									in.set(verify[1], new intVal(name, in.get(verify[1]).value + scan2.nextInt()));
								}
								else {
									String s = scan2.next();
									int[] assist = exist(s);
									if (assist[0] == 0)
										in.set(verify[1],
												new intVal(name, in.get(verify[1]).value + in.get(assist[1]).value));
									else {
										broke = true;
										System.out.println("RUNTIME ERROR 2 ON LINE " + line);
										break;
									}
								}
							}
						} else if (verify[0] == 1) { // Variable is a String
							if (scan2.hasNext("\\+=")) {
								scan2.next();
								if (scan2.hasNextInt()){
									System.out.println("RUNTIME ERROR 3 ON LINE " + line);
									break;
								}
								else {
									String s = scan2.next();
									int[] assist = exist(s);
									if (assist[0] == 1)
										str.set(verify[1],
												new strVal(name, str.get(verify[1]).value + str.get(assist[1]).value));
									else if(assist[0] == 0) {
										broke = true;
										System.out.println("RUNTIME ERROR 4 ON LINE " + line);
										break;
									}
									else
										str.set(verify[1], new strVal(name,str.get(verify[1]).value+s));
								}
							}
						}

						if (scan2.hasNext("\\*=")) {
							scan2.next();
							if (scan2.hasNextInt() && verify[0] == 1){
								System.out.println("RUNTIME ERROR 5 ON LINE " + line);
								broke=true;
								break;
							}
							else if (scan2.hasNextInt() && verify[0] == 0)
								in.set(verify[1], new intVal(name, in.get(verify[1]).value * scan2.nextInt()));
							else if (!scan2.hasNextInt() && verify[0] == 1){
								System.out.println("RUNTIME ERROR 6 ON LINE " + line);
								broke=true;
								break;
							}
							else {
								String hold = scan2.next();
								int[] poss = exist(hold);
								if (poss[0] == 0)
									in.set(verify[1],
											new intVal(name, in.get(verify[1]).value * in.get(poss[1]).value));
								else{
									System.out.println("RUNTIME ERROR 7 ON LINE " + line);
									broke=true;
									break;
								}
							}

						}
					
						scan2.close();
						continue;
					}
					if (scan1.hasNext("~")) {
						scan1.next();
						count++;
						scan1 = new Scanner(content.get(count));
						if(scan1.hasNext() && !scan1.hasNext("~"))
						forEnds.set(count, forEnds.get(count)-1);
						continue;
					}

					else if (scan1.hasNext(";")) {
						scan1.next();
						continue;
					} else {
						String name = scan1.next();
						int[] verify = exist(name);
						if (verify[0] == 0) { // Variable is an int
							if (scan1.hasNext("-=")) {
								scan1.next();
								if (scan1.hasNextInt())
									in.set(verify[1], new intVal(name, in.get(verify[1]).value - scan1.nextInt()));
								else {
									String s = scan1.next();
									int[] assist = exist(s);
									if (assist[0] == 0)
										in.set(verify[1], new intVal(name, in.get(verify[1]).value - in.get(assist[1]).value));
									else {
										System.out.println("RUNTIME ERROR 8 ON LINE " + line);
										broke=true;
										break;
									}
								}
							}
							if (scan1.hasNext("\\+=")) {
								scan1.next();
								if (scan1.hasNextInt()){
									in.set(verify[1], new intVal(name, in.get(verify[1]).value + scan1.nextInt()));
								}
								else {
									String s = scan1.next();
									int[] assist = exist(s);
									if (assist[0] == 0)
										in.set(verify[1], new intVal(name, in.get(verify[1]).value + in.get(assist[1]).value));
									else {
										broke = true;
										System.out.println("RUNTIME ERROR 9 ON LINE " + line);
										break;
									}
								}
							}
						} else if (verify[0] == 1) { // Variable is a String
							if (scan1.hasNext("\\+=")) {
								scan1.next();
								if (scan1.hasNextInt()){
									broke=true;
									System.out.println("RUNTIME ERROR 10 ON LINE " + line);
									break;
								}
								else {
									String s = scan1.next();
									int[] assist = exist(s);
									if (assist[0] == 1)
										str.set(verify[1],
												new strVal(name, str.get(verify[1]).value + str.get(assist[1]).value));
									else if(assist[0]==0){
										broke = true;
										System.out.println("RUNTIME ERROR 11 ON LINE " + line);
										break;
									}
									else {
										str.set(verify[1], new strVal(name,str.get(verify[1]).value+s));
									}
								}
							}
						}

						if (scan1.hasNext("\\*=")) {
							scan1.next();
							if (scan1.hasNextInt() && verify[0] == 1){
								broke=true;
								System.out.println("RUNTIME ERROR 12 ON LINE " + line);
								break;
							}
							else if (scan1.hasNextInt() && verify[0] == 0)
								in.set(verify[1], new intVal(name, in.get(verify[1]).value * scan1.nextInt()));
							else if (!scan1.hasNextInt() && verify[0] == 1){
								broke=true;
								System.out.println("RUNTIME ERROR 13 ON LINE " + line);
								break;
							}
							else {
								String hold = scan1.next();
								int[] poss = exist(hold);
								if (poss[0] == 0)
									in.set(verify[1],
											new intVal(name, in.get(verify[1]).value * in.get(poss[1]).value));
								else{
									broke=true;
									System.out.println("RUNTIME ERROR 14 ON LINE " + line);
									break;
								}
							}

						}
					}
					scan1.next();
					if (!scan1.hasNext()) {
						forEnds.set(count, forEnds.get(count) - 1);
						scan1 = new Scanner(content.get(count));
						if (forEnds.get(count) == 0 && count != 0) {
							forEnds.set(count, original.get(count));
							count--;
						}
					}
				}
				
				if(place>0 && content.get(0).contains(rest.get(place-1))){
					scan1 = new Scanner(rest.get(place-1));
					String name = scan1.next();
					int[] verify = exist(name);
					if(verify[0]==0){
					if (scan1.hasNext("\\+=")) {
						scan1.next();
						if (scan1.hasNextInt()){
							in.set(verify[1], new intVal(name, in.get(verify[1]).value + scan1.nextInt()));
						}
						else {
							String s = scan1.next();
							int[] assist = exist(s);
							if (assist[0] == 0)
								in.set(verify[1],
										new intVal(name, in.get(verify[1]).value + in.get(assist[1]).value));
							else {
								broke = true;
								System.out.println("RUNTIME ERROR 15 ON LINE " + line);
								break;
							}
						}
					}
				}else if (verify[0] == 1) { // Variable is a String
					if (scan1.hasNext("\\+=")) {
						scan1.next();
						if (scan1.hasNextInt()){
							broke=true;
							System.out.println("RUNTIME ERROR 10 ON LINE " + line);
							break;
						}
						else {
							String s = scan1.next();
							int[] assist = exist(s);
							if (assist[0] == 1)
								str.set(verify[1],
										new strVal(name, str.get(verify[1]).value + str.get(assist[1]).value));
							else if(assist[0]==0){
								broke = true;
								System.out.println("RUNTIME ERROR 11 ON LINE " + line);
								break;
							}
							else {
								str.set(verify[1], new strVal(name,str.get(verify[1]).value+s));
							}
						}
					}
				}
				}

				scan1.close();
			}

			if (broke)
				break;

			if (scan.hasNext("PRINT")) {
				scan.next();
				print(scan.next());
			}

			if (!scan.hasNext("PRINT") && !scan.hasNext("FOR")) {
				if (!scan.hasNext())
					break;
				String name = scan.next();
				if (scan.hasNext("=")) {
					scan.next();
					int[] verify = exist(name);

					if (scan.hasNextInt() && verify[0] == 1) {
						str.set(verify[1], str.get(str.size - 1));
						str.set(str.size - 1, null);
						str.size--;
					} else if (!scan.hasNextInt() && verify[0] == 0) {
						String test = scan.nextLine();
						Scanner scan1 = new Scanner(test);
						String hold = scan1.next();
						hold = hold.replaceAll(";", "");
						int[] help = exist(hold);
						if (help[0] == 0) {
							in.set(verify[1], new intVal(name, in.get(help[1]).value));
							continue;
						}
						if (hold.charAt(0) == '\"') {
							hold = hold.replaceAll("\"", "");
							in.set(verify[1], in.get(in.size - 1));
							in.set(in.size - 1, null);
							in.size--;
							str.add(new strVal(name, hold));
						} else {
							in.set(verify[1], in.get(in.size - 1));
							in.set(in.size - 1, null);
							in.size--;
						}
						scan1.close();
					}

					else if (scan.hasNextInt() && verify[0] == 0)
						in.get(verify[1]).value = scan.nextInt();

					else if (!scan.hasNextInt() && verify[0] == 1) {
						str.get(verify[1]).value = scan.next();
					}

					else if (scan.hasNextInt() && verify[0] == -1) {
						in.add(new intVal(name, scan.nextInt()));
					} else {
						String hold = scan.next();
						int[] possible = exist(hold);
						if (possible[0] == 0) {
							in.add(new intVal(name, in.get(possible[1]).value));
						} else
							str.add(new strVal(name, hold.replaceAll("\"", "")));
					}

				} else if (scan.hasNext("\\+=")) {
					int[] loc = exist(name);
					if (loc[0] < 0) {
						System.out.println("ERROR 4 on line: " + line);
						break;
					} else {
						scan.next();
						if (loc[0] == 1) {
							if (scan.hasNextInt()) {
								System.out.println("RUNTIME ERROR 16 ON LINE " + line);
								break;
							} else {
								String variable = scan.next();
								int[] vari = exist(variable);
								if (vari[0] == 1)
									str.set(loc[1], new strVal(name, str.get(loc[1]).value + str.get(vari[1]).value));
								else
									str.get(loc[1]).value += variable;
							}
						} else {
							if (scan.hasNextInt())
								in.get(loc[1]).value += scan.nextInt();
							else {
								int hold = exist(scan.next())[1];
								if (hold > -1)
									in.get(loc[1]).value += in.get(hold).value;
								else {
									System.out.println("RUNTIME ERROR 17 ON LINE " + line);
									break;
								}
							}
						}
					}
				}

				else if (scan.hasNext("\\*=")) {
					int[] loc = exist(name);
					if (loc[0] < 0)
						System.out.println("ERROR 4");
					else {
						if (loc[0] == 1){
							System.out.println("RUNTIME ERROR 18 ON LINE " + line);
							break;
						}
						else {
							scan.next();
							if (scan.hasNextInt())
								in.get(loc[1]).value *= scan.nextInt();
							else
								in.get(loc[1]).value *= in.get(exist(scan.next())[1]).value;
						}
					}
				}

				else if (scan.hasNext("-=")) {
					int[] loc = exist(name);
					if (loc[0] < 0)
						System.out.println("ERROR: Incorrect Syntax");
					else {
						if (loc[0] == 1)
							System.out.println("ERROR: Incorrect Syntax");
						else {
							scan.next();
							if (scan.hasNextInt())
								in.get(loc[1]).value -= scan.nextInt();
							else {
								in.get(loc[1]).value -= in.get(exist(scan.next())[1]).value;
							}
						}
					}
				}
			}
		}
		scan.close();
		long end = System.currentTimeMillis();;
		System.out.println("========================================\nTime to execute: "+ (end-start)+" milliseconds");
	}

	private static int[] exist(String val) {
		for (int i = 0; i < str.size(); i++)
			if (str.get(i).getName().equals(val))
				return new int[] { 1, i }; // 1 indicates the string array
		for (int i = 0; i < in.size(); i++)
			if (in.get(i).getName().equals(val))
				return new int[] { 0, i }; // 0 indicates the integer array
		return new int[] { -1, -1 };
	}

	private static void print(String var) {
		int[] ex = exist(var);
		int spot = ex[1];
		if (spot < 0)
			System.out.println("Print Error");
		else {
			if (ex[0] == 1)
				System.out.println(str.get(spot).value);
			else
				System.out.println(in.get(spot).value);
		}
	}

	private static class intVal {
		String name;
		int value;

		private intVal(String nam, int val) {
			name = nam;
			value = val;
		}

		private String getName() {
			return name;
		}
	}

	private static class strVal {
		String name;
		String value;

		private strVal(String nam, String val) {
			name = nam;
			value = val;
		}

		private String getName() {
			return name;
		}
	}
}