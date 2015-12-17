package UMLRequestsPromo;

import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
import java.util.Scanner;

public class Main {

	// each promotion has its own identifier
	static int identifier = 0; 
	
	//a try with hashmap was done but unsuccessful
	//Hash map correlating clients with its respective promotions
	//static Map<String, List<Promotion>> promotionOwners = new HashMap<> ();
	//Hash map correlating clients with its respective promotions with draft project ready
	//static Map<String, List<Promotion>> draftProjectOwners = new HashMap<> ();

	//list of clients
	static List<Client> clientsList = new ArrayList<Client> ();

	//list of all promotions
	static List<Promotion> promotionList = new ArrayList<Promotion> ();

	//list of promotions with draft projects ready to be analyzed
	static List<Promotion> draftProjectList = new ArrayList<Promotion> ();

	//list of promotions that are waiting for draft processes
	static List<Promotion> pendingList = new ArrayList<Promotion> ();
	
	//list of promotions that were started
	static List<Promotion> startedList = new ArrayList<Promotion> ();
	
	//list of promotions that were completed and are waiting payment
	static List<Promotion> completedList = new ArrayList<Promotion> ();
	
	//index to correlate clients with the respective projects in the both lists 
	// (solution found after the try to use hash map not worked as expected)
	static int index = 0;
	
	static String userType = null; //can be Client or Admin
	
	static Scanner sc; //a scanner is initialized to read inputs
	
	static Client client = null;
	static Staff employee;
	
	//auxiliary variables
	static boolean test;
	static String aux;	
	static int var, i = 0, tempIndex = 0;
	static double aux2;

	public static void main(String[] args) {

		
		
		System.out.println("Hello, Welcome to BusyPoint!");
		
		// program runs until user explicit exits
		while (true)
		{
			// the user choose between client mode and admin mode
			var = -1;
			while (var < 0 || var > 2)
			{
				System.out.println("\nPress: \n\n"
						+ "1. To log in as Client \n"
						+ "2. To log in as Admin (password necessary)\n"
						+ "0. To exit the program.");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}

			//login switch
			switch (var)
			{
			case 1:
			{
				// Client needs an identification to log in (name of company)
				System.out.println("\nInsert the name of the company:");
				sc = new Scanner (System.in);
				aux = sc.nextLine();

				//checks if the client is new or not
				client = addClient(aux);

				//userType receive the name of the company
				userType = aux;
				break;	
			}
			case 2:
			{
				// Admin needs a password to log in (for the example will
				// be just a single password but in the final program, there will
				// be needed a lot of passwords, one for each employee using
				// the system. There will be needed also a way to change the 
				// password and create new users.
				System.out.println("\nInsert the password:");
				sc = new Scanner (System.in);
				aux = sc.nextLine();

				//check if the password is correct
				if (aux.equals("busypoint123"))
					userType = "Admin";
				else
				{
					// if the password is not correct, the program goes back to the start screen
					System.out.println("Incorrect password, going back to the start screen...");
					var = -1;
				}
				break;
			}
			case 0:
				return;
			}

			// if the user is a client or a valid admin
			if (var == 1 || var == 2)
				System.out.printf("\nYou are logged in as: "+ userType + "\n");

			switch (var)
			{
			// if the user is a client
			case 1:
			{
				//generates the menu for the client and retorns an option
				
				var = menuClient();
				
				
				switch (var)
				{
				case 1:
				{
					var = -1;
					while (var < 0 || var > 7)
					{
						System.out.println("\nChose the desirable type: \n\n"
								+ "1. To a Magazine advert. \n"
								+ "2. To a Newspaper advert. \n"
								+ "3. To a Poster advert. \n"
								+ "4. To a Radio advert. \n"
								+ "5. To a Tv advert. \n"
								+ "6. To a Web advert. \n"
								+ "0. To an Email advert. \n"
								+ "0. To go back to the main page.");

						sc = new Scanner (System.in);
						var = sc.nextInt();
					}
					
					/*tempIndex = -1;
					for(Promotion t : promotionList)
					{
						tempIndex++;
						if(t.identifier == identifier)
							index = tempIndex;
					}*/

					switch (var)
					{
					case 1:
						promotionList.add(new MagazineAdverts(identifier++, client));
						break;
					case 2:
						promotionList.add(new NewspaperAdvert(identifier++, client));
						break;
					case 3:
						promotionList.add(new PosterAdvert(identifier++, client));
						break;
					case 4:
						promotionList.add(new RadioAdvert(identifier++, client));
						break;
					case 5:
						promotionList.add(new TvAdvert(identifier++, client));
						break;
					case 6:
						promotionList.add(new WebAdvert(identifier++, client));
						break;
					case 7:
						promotionList.add(new EmailAdvert(identifier++, client));
						break;
					}

					//promotionOwners.put(a[index].getName(), promotionList);
					break;
				}
				//view draft projects
				case 2:
				{
					tempIndex = 1;
					// shows every draft project existent for the given client
					for (Promotion t: draftProjectList)
						if (t.client.getName().equals(userType))
						{
							System.out.println("=========================================");
							System.out.println("Promotion " + tempIndex++ + ":" +
											"\nPromotion type: " + t.description.getType() +
											"\nDetails: " + t.description.getDetails() +
											"\nCost: " + t.getCost() +
											"\nEnd Date:" + t.description.getEndDate());
							System.out.println("==========================================");
						}

					var = -1;
					while (var < 0 || var > tempIndex)
					{
						System.out.println("\nPress 0 to go back to the main page or the promotion's "
										+ "respective number to choose an action.");

						sc = new Scanner (System.in);
						var = sc.nextInt();
					}
					
					if (var > 0)	
					{				
						/*i = 0;
						for(Map.Entry<String, List<Promotion>> entry : draftProjectOwners.entrySet())
						{ 
							for (Promotion t: entry.getValue())
							{		
								if (t.identifier == draftProjectList.get(c-1).identifier)
									k = i;

								i++;
							}
						}*/
						
						tempIndex = var - 1;
						var = -1;
						// find the draft project respective promotion index in the promotionList	
						for (Promotion t: promotionList)
						{
							var++;
							if (t.identifier == draftProjectList.get(tempIndex).identifier)
							{
								i = var;
							}
						}
						tempIndex = i;
						
						var = -1;
						while (var < 0 || var > 2)
						{
							System.out.println("\nChoose the desirable action: \n\n"
											+ "0. To cancel. \n"
											+ "1. To confirm promotion.\n"
											+ "2. To cancel promotion.");

							sc = new Scanner (System.in);
							var = sc.nextInt();
						}

						switch (var)
						{
						case 1:
						{
							/*i = 0;
							for(Map.Entry<String, List<Promotion>> entry : promotionOwners.entrySet())
							{
								for (Promotion t: entry.getValue())
								{
									if (t.identifier == draftProjectList.get(k).identifier)
									{
										promotionList.get(i).setConfirmed(true);
										promotionOwners.put(entry.getKey(), promotionList);		
										draftProjectList.remove(draftProjectList.get(k));
									}
									i++;
								}
							}	*/
							
							promotionList.get(tempIndex).setConfirmed(true);
							draftProjectList.remove(promotionList.get(tempIndex));
							
							//after confirmation, a client issues a Purchase Order number for a given promotion
							System.out.println("\nWhat's the Purchase Order (PO) number?");
							sc = new Scanner (System.in);
							var = sc.nextInt();
							
							promotionList.get(tempIndex).setPonumber(var);
							
							startedList.add(promotionList.get(tempIndex));
							break;
						}	
						case 2:
						{
							/*i = 0;
							for(Map.Entry<String, List<Promotion>> entry : promotionOwners.entrySet())
							{ 
								for (Promotion t: entry.getValue())
								{
									if (t.identifier == draftProjectList.get(k).identifier)
									{
										promotionList.remove(i);
										promotionOwners.put(entry.getKey(), promotionList);		
										draftProjectList.remove(draftProjectList.get(k));
									}
									i++;
								}
							}*/	
							
							draftProjectList.remove(promotionList.get(tempIndex));
							promotionList.remove(tempIndex);
							clientsList.remove(tempIndex);
												
							break;
						}
						}
					}
					break;
				}
				
				//make payment
				case 3:
				{
					tempIndex = 1;
					for (Promotion t: completedList)
						// show the completed promotions of the given client that are waiting payment
						if (t.client.getName().equals(userType))
						{
							System.out.println("=========================================");
							System.out.println("Promotion " + tempIndex++ + ":" +
											"\nPromotion type: " + t.description.getType() +
											"\nDetails: " + t.description.getDetails() +
											"\nCost: " + t.getCost() +
											"\nEnd Date:" + t.description.getEndDate());
							System.out.println("==========================================");
						}

					var = -1;
					while (var < 0 || var > tempIndex)
					{
						System.out.println("\nPress 0 to go back to the main page or the promotion's "
										+ "respective number to make payment.");
						sc = new Scanner (System.in);
						var = sc.nextInt();

						if (var > 0)
						{						
							tempIndex = var - 1;
							var = -1;
							// find the invoice respective promotion index in the promotionList	
							for (Promotion t: completedList)
							{
								var++;
								if (t.identifier == completedList.get(tempIndex).identifier)
								{
									i = var;
								}
							}
							tempIndex = i;
							
							promotionList.get(tempIndex).payment.setPaymentMade(true);
							completedList.remove(promotionList.get(tempIndex));
						}
						break;
					}
		
				break;
				}
				}
				break;
			}
			//if the user is an admin
			case 2:
			{
				var = adminMenu();
				
				switch (var)
				{
				//to deal with pending projects
				case 1:
				{
					System.out.println("\nPending promotions: \n");

					/*for(Map.Entry<String, List<Promotion>> entry : promotionOwners.entrySet())
					{ 
						for (Promotion t: entry.getValue())
							if (t.isPending())
							{
								System.out.println(i++ + ". Client: " + t.client.getName() + 
											"  ->  Promotion: " +  t.description.getType());
								pending.add(t);
							}
					}*/
							
					i = 0;
					for (Promotion t: promotionList)
					{
								
						if (t.isPending())
						{
							System.out.println(++i + ". Client: " + t.client.getName() + 
										"  ->  Promotion: " +  t.description.getType());
							pendingList.add(t);
						}
					}
							
					var = -1;
					while (var < 0 || var > i)
					{
						System.out.println("\nChoose the respective number to create and send\n"
									+ "a draft for the client or press 0 to go back to the main page.");

						sc = new Scanner (System.in);
						var = sc.nextInt();
					}

					if (var > 0)
					{
					
						//int x = -1;
	
						/*i = 0;
						for(Map.Entry<String, List<Promotion>> entry : promotionOwners.entrySet())
						{ 
							for (Promotion t: entry.getValue())
							{		
								if (t.identifier == pending.get(c-1).identifier)
								{
									j = i;
								}
								i++;
							}
						}*/
	
						tempIndex = var - 1;
						var = -1;
						// find the draft project respective promotion index in the promotionList	
						for (Promotion t: promotionList)
						{
							var++;
							if (t.identifier == pendingList.get(tempIndex).identifier)
							{
								i = var;
							}
						}
						tempIndex = i;
								
						do 
						{
							var = -1;
							while (var < 0 || var > 4)
							{
								System.out.println("\nInsert the option desired to update the Promotion draft:\n\n"
												+ "1. Update type.\n"
												+ "2. Update end date.\n"
												+ "3. Update details.\n"
												+ "4. Update cost.\n"
												+ "0. To go back to the main page.");
	
								sc = new Scanner (System.in);
								var = sc.nextInt();
							}
	
							switch (var)
							{
							case 1:
							{
								System.out.println("\nInsert the new type.");
								sc = new Scanner (System.in);
								aux = sc.nextLine();
								promotionList.get(tempIndex).description.setType(aux);
								break;
							}
	
							case 2:
							{
										/*int year,month,day;
	
										System.out.println("\nInsert the end date:");
										System.out.println("\nyear:");
										sc = new Scanner (System.in);
										year = sc.nextInt();
	
										System.out.println("\nmonth:");
										sc = new Scanner (System.in);
										month = sc.nextInt();
	
										System.out.println("\nday:");
										sc = new Scanner (System.in);
										day = sc.nextInt();
	
	
										temp.description.setEndDate(year,month,day);
										break;*/
							}
	
							case 3:
							{
								System.out.println("\nInsert details.");
								sc = new Scanner (System.in);
								aux = sc.nextLine();
								promotionList.get(tempIndex).description.setDetails(aux);
								break;
							}
	
							case 4:
							{
								System.out.println("\nInsert cost.");
								sc = new Scanner (System.in);
								aux2 = sc.nextDouble();
								promotionList.get(tempIndex).description.setCost(aux2);
								break;
							}
							}
	
							var = -1;
							while (var != 0 && var != 1)
							{
								System.out.println("\nIs there any aditional information that need to be changed?\n\n"
												+ "0. Yes.\n"
												+ "1. No.");
								sc = new Scanner (System.in);
								var = sc.nextInt();
							}
							
						}while (var == 0);
			
						draftProjectList.add(promotionList.get(tempIndex));
						promotionList.get(tempIndex).setPending(false);
					}
					break;
				}
				// to deal with started projects
				case 2:
				{
					System.out.println("\nStarted promotions: \n");
							
					i = 0;
					for (Promotion t: startedList)
						System.out.println(++i + ". Client: " + t.client.getName() + 
										"  ->  Promotion: " +  t.description.getType());
					
					var = -1;
					while (var < 0 || var > i)
					{
						System.out.println("\nChoose the respective number to set a promotion\n"
									+ "as completed or press 0 to go back to the main page.");

						sc = new Scanner (System.in);
						var = sc.nextInt();
					}
					
					if (var > 0)
					{
						tempIndex = var - 1;
						var = -1;
						// find the given promotion set as completed in the promotionList	
						for (Promotion t: promotionList)
						{
							var++;
							if (t.identifier == startedList.get(tempIndex).identifier)
							{
								i = var;
							}
						}
						tempIndex = i;
						
						// promotion is set as completed
						promotionList.get(tempIndex).setCompleted(true);
						startedList.remove(promotionList.get(tempIndex));
					
						//add invoice to be payed in invoice list
						promotionList.get(tempIndex).setInvoice(promotionList.get(tempIndex).description.getCost());
						completedList.add(promotionList.get(tempIndex));
					}
					break;
				}
				}

					
				
				
			}
			break;
		}	
	}
	}
	
	public static Client addClient(String aux)
	{
		
		
		//a test is done to check if the client already exist in the database
		test = false;
		for(Client cl : clientsList) 
			if(cl.getName().equals(aux)) 
				test = true;

		//if client does not exist, a member of staff is assigned to the client
		
		if (!test)
		{
			// (for the example, a common staff employee will be used for all clients
			// but in the final program it will be already in the database)
			employee = new Staff (435, 2700.00, "account manager", 7);

		}
		//else, if client already exists
		else
		{
			// find the client in the list
			for(Client cl : clientsList) 
				if(cl.getName().equals(aux)) 
					client = cl;
			
			//recover its employee info
			employee = client.getStaffAssigned();
			
		}
		
		//the company name will be the variable inserted when the client
		//logged in the system and the main contact in the company will be
		//"contact 1" in this example but must be added in the database
		//in the final program. The client is added to the clientsList
		return client = new Client(aux,"contact 1", employee);
	}
	
	public static int menuClient()
	{
		var = -1;
		//check if there are draft projects ready to be analyzed
		int df = 0;
		for(Promotion t : draftProjectList) 
			if(t.client.getName().equals(userType))
				df++;
		
		//check if there are completed projects waiting payment
		int cp = 0;
		for(Promotion t : completedList) 
			if(t.client.getName().equals(userType))
				cp++;
		
		//if there is not any draft projects and any completed projects ready 
		if (df == 0 && cp == 0)
		{	
			while (var != 0 && var != 1)
			{
				System.out.println("\nPress: \n"
						+ "1. To request a promotion.\n"
						+ "0. To exit the program");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}
		}
		
		//if there is at least one draft project ready and any completed projects waiting payment
		else if (df != 0 && cp == 0)
		{
			System.out.println("\nYOU HAVE " + df +" DRAFT PROJECT(S) TO BE ANALYZED");
			while (var < 0 || var > 2)
			{
				System.out.println("\nPress: \n"
						+ "1. To request a promotion.\n"
						+ "2. To view draft projects.\n"
						+ "0. To go back to the main page.");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}
		}
		
		//if there is not any draft project ready and at least one completed process waiting payment
		else if (df == 0 && cp != 0)
		{
			System.out.println("\nYOU HAVE " + cp +" PROJECT(S) WAITING PAYMENT");
			while ((var < 0 || var > 3) || var == 2)
			{
				System.out.println("\nPress: \n"
						+ "1. To request a promotion.\n"
						+ "3. To view projects waiting payment.\n"
						+ "0. To go back to main page.");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}
		}
		
		//if there is at least one draft project and at least one completed process waiting payment
		else if (df != 0 && cp != 0)
		{
			System.out.println("\nYOU HAVE " + df +" DRAFT PROJECT(S) TO BE ANALYZED\n");
			System.out.println("\nYOU HAVE " + cp +" PROJECT(S) WAITING PAYMENT");
			while (var < 0 || var > 3)
			{
				System.out.println("\nPress: \n"
						+ "0. To exit the program. \n"
						+ "1. To request a promotion.\n"
						+ "2. To view draft projects.\n"
						+ "3. To view projects waiting payment.");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}
		}
		return var;
	}
	
	public static int adminMenu()
	{
		boolean pp = false;
		boolean sp = false;
		var = -1;
		//check if there are pending projects ready to be analyzed or started projects
		//that can be set to completed
		for(Promotion t : promotionList) 
			{
				if (t.isPending())
					pp = true;
				if (t.isConfirmed())
					sp = true;				
			}
		
		if (pp == false && sp == false)
			System.out.println("There are no actions to be done. Going back to start screen.");
		else if (pp && sp == false)
		{
			while (var != 0 && var != 1)
			{
				System.out.println("\nChoose the desirable action: \n\n"
							+ "1. To check for pending promotions \n"
							+ "0. To go back to the main page.");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}
		}
		else if (pp == false && sp)
		{
			while (var != 0 && var != 2)
			{
				System.out.println("\nChoose the desirable action: \n\n"
							+ "2. To set a promotion as completed \n"
							+ "0. To go back to the main page.");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}
		}
		else 
		{
			while (var < 0 || var > 2)
			{
				System.out.println("\nChoose the desirable action: \n\n"
							+ "1. To check for pending promotions \n"
							+ "2. To set a promotion as completed \n"
							+ "0. To go back to the main page.");

				sc = new Scanner (System.in);
				var = sc.nextInt();
			}
		}
		return var;
	}
}