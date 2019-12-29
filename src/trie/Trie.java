package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		                      
	    TrieNode root = new TrieNode(null, null, null);
        TrieNode nextChild = root;
        TrieNode nextSibling = root;
        
        if (allWords.length == 0) {
        	
        	return null;
        	
        }
        
        for (int i = 0; i < allWords.length; i++) {
            
            // This occurs only once. Adds in the first string of the array, so that the trie is
            // not empty.
            
            
            if (i == 0) {
                short beginning = 0;
                short ending = 0;
                for (short j = 0; j < allWords[i].length(); j++) {
                    if (j + 1 == allWords[i].length()) {
                        ending = j;
                    }
                }
                Indexes index = new Indexes(0, beginning, ending);
                TrieNode wordNode = new TrieNode(index, null, null);
                root.firstChild = wordNode;
                continue;
            }
            
            // Every single string following the first one will always go into this loop. 
            // Checks 
            
            TrieNode ptr = root.firstChild;
            TrieNode prev = root;
            String currentWord = allWords[i];
            short startingPoint = 0;
            System.out.println("What is the value of the currentWord?: " + currentWord);
            
            while (ptr != null) {
            	
            	
            	// This condition checks for words that would share the same parent
                // Rearranges the current node
                // Creates the parents and siblings nodes that fall within the parent
                
            	System.out.println("the value of startingPoint should eventually be 2: " + startingPoint);
            	System.out.println("Current word starting point: " + currentWord.charAt(startingPoint));
            	System.out.println("Current ptr starting point: " + allWords[ptr.substr.wordIndex].charAt(startingPoint));
            	
                if (currentWord.charAt(startingPoint) == allWords[ptr.substr.wordIndex].charAt(startingPoint)) {
                     
                    System.out.println("Does doom come here?: " + currentWord);
                    System.out.println("And what is the value of ptr?: " + allWords[ptr.substr.wordIndex]);
                    System.out.println("And what is the value of the indexes?: " + allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex, ptr.substr.endIndex+1));
                    
                    short ending = 0;
                    for (short j = startingPoint; j < currentWord.length(); j++) {
                    	if (currentWord.charAt(j) != allWords[ptr.substr.wordIndex].charAt(j)) {
                    		break;
                    	}
                    	if (j > ptr.substr.endIndex) {
                    		break;
                    	}
                    	ending = j;
                    }
                    
                    if (ending == ptr.substr.endIndex) {
                    	
                    	startingPoint = (short) ((short) ending + 1);
                    	
                    	prev = ptr;
                    	ptr = ptr.firstChild;
                    	
                    	continue;
                    	
                    	
                    } else {
                    	
                    	System.out.println("does possum go in here?");
                    	
                    	Indexes index = new Indexes(ptr.substr.wordIndex, startingPoint, ending);
                    	TrieNode node = new TrieNode(index, null, null);
                    	
                    	Indexes childIndex = new Indexes(ptr.substr.wordIndex, (short) ((short) ending + 1), ptr.substr.endIndex);
                    	TrieNode childNode = new TrieNode(childIndex, null, null);
                    	
                    	Indexes siblingIndex = new Indexes(i, (short) ((short) ending + 1), (short) ((short)currentWord.length() - 1));
                    	TrieNode siblingNode = new TrieNode(siblingIndex, null, null);
                    	
                    	if (prev == root || prev.firstChild == ptr) {
                    		prev.firstChild = node;
                    	} else {
                    		prev.sibling = node;
                    	}
                    	
                    	childNode.sibling = siblingNode;
                    	node.firstChild = childNode;
                    	node.sibling = ptr.sibling;
                    	
                    	if (ptr.firstChild != null) {
                    		childNode.firstChild = ptr.firstChild;
                    	}
                    	
                    	break;
                    }
            
                    // This condition checks for words that would not belong to same tree
                    // So primarily creates siblings
                
                } else if (ptr.sibling == null && currentWord.charAt(startingPoint) != allWords[ptr.substr.wordIndex].charAt(startingPoint)){
                	
                	System.out.println("Are you in this loop?");
                	
                	short ending = 0;
                	
                	for (short j = startingPoint; j < currentWord.length(); j++) {
                		ending = j;
                	}
                	
                	Indexes index = new Indexes(i, startingPoint, ending);
                	TrieNode node = new TrieNode(index, null, null);
                
                    ptr.sibling = node;
                    
                    System.out.println("thesjcnasjdnsajnad: " + node.substr.wordIndex);
                    
                    break;
                
                // For situations where they may be more than two different "types" of words
                // Traverses the siblings until it finds a match, or doesn't find a match
                // and eventually creates its own sibling
               
                } else {
                    
                	prev = ptr;
                    ptr = ptr.sibling;
                
                }
            }
        }
		
		
		
		
		
		
		
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return root;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<TrieNode> prefixList = new ArrayList<TrieNode>();
		TrieNode ptr = root.firstChild;
		String prefixBuilder = "" ;
		
		if (ptr == null || allWords.length == 0 || prefix.length() == 0) {
			
			System.out.println("So do you go through here?");
			
			return null;
			
		}
		
		while (ptr != null) {
			
			System.out.println("So that means that you actually run then huh: " + "(" + ptr.substr.wordIndex + "," + ptr.substr.startIndex + "," + ptr.substr.endIndex + ")");
			
			if (prefix.charAt(ptr.substr.startIndex) == allWords[ptr.substr.wordIndex].charAt(ptr.substr.startIndex)) {
				//you want to check if ALL the letters are equal, not just the first character
				
				
				for (int i = ptr.substr.startIndex; i <= ptr.substr.endIndex; i++) {
					//put condition in here to check if all the characters are equal
					//if not equal, need to move to the sibling
					//if it is equal, but you haven't reached the end of the prefix, go to the child
					//if it is equal, and you are at the end of the prefix, move to the firstChild and recursively 
					
					if (i == prefix.length()) {
						break;
					}
					
					if (prefix.charAt(i) != allWords[ptr.substr.wordIndex].charAt(i)) {
						
						return null;
						
					}
					
					prefixBuilder += prefix.charAt(i);
					
					if (prefixBuilder.equals(prefix)) {
						break;
					}
				
				}
				
				System.out.println("pls man: " + prefixBuilder);
				
				if (prefixBuilder.equals(prefix)) {
					
					System.out.println("You should NEVER come in here.");
					
					if (ptr.firstChild == null) {
						
						prefixList.add(ptr);
						break;
						
					}
					
					TrieNode runner = ptr.firstChild;
					
				//	while (runner != null) {
						
				//		if (runner.firstChild == null) {
			//				prefixList.add(runner);
			//			} else {
							prefixList = recurseMethod(runner, prefixList);
				//		}
						
						/*
						
						while (runner != null) {
							
							if (runner.firstChild == null) {
								
								prefixList.add(runner);
								runner = runner.sibling;
								
							} else {
								
								runner = runner.firstChild;
								
							}
						}
						
						*/
						
				//		runner = runner.sibling;
						
				//	}
					
					break;
					
				} else {
					
					ptr = ptr.firstChild;
					
					continue;
				
				}
				
			} else {
				
				ptr = ptr.sibling;
			
			}
			
		}
		
		if (prefixBuilder == "" || prefixList == null || !(prefixBuilder.equals(prefix))) {
			
			return null;
			
		}
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return prefixList;
	}
	
	private static ArrayList<TrieNode> recurseMethod(TrieNode runner, ArrayList<TrieNode> prefixList) {
		
		while (runner != null) {
			if (runner.firstChild == null) {
				System.out.println("How many times do you loop the recursion man?");
				prefixList.add(runner);
			} else {
				prefixList = recurseMethod(runner.firstChild, prefixList);
			}
			
			runner = runner.sibling;
			
		}
		
		return prefixList;
		
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
