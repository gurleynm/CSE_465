import sys

tsv = sys.argv[1] # a.tsv is the <<NAME>> version
tmp = sys.argv[2] # a.tmp is the "Steve Smith" version replacement

def indi(index):
	with open(tsv, "rt") as input:
		for x in range(index+1):
			input.readline()
		id = input.readline()
		id = id.split()
	return id

if(tsv == "a.tsv"):
	with open(tmp, "rt") as outline, open(tsv,"rt") as file:
		arr = outline.readlines()
		held = []
	
		for i in range(len(arr)):
			held.append(arr[i])
			
		lines = file.readlines()
		for j in range(len(lines)-1):
			info = indi(j)
			
			id = info[2]
			name = info[0] + " " + info[1]
			course = info[3]
			due = info[4] + " " + info[5]				
			total = info[len(info)-1]
			subtotal = info[len(info) - 2]
			
			if(info[6] == "ontime"):
				submitted = "ontime"
				late = str(0)
				deduct = str(0)
				p1 = info[9]
				
				# Defining P1 Comments
				hold = ""
				i=10
				num = int(subtotal) - int(p1)
				while(info[i].isalpha and info[i] != str(num)):
					hold+=info[i] + " "
					i = i + 1
				p1com = hold.rstrip()
			else:
				submitted = info[6]+ " " + info[7]
				late = info[8]
				deduct = info[9]
				p1 = info[10]
				
				# Defining P1 Comments
				hold=""
				i=11
				num = int(subtotal) - int(p1)
				while(info[i].isalpha and info[i] != str(num)):
					hold+=info[i] + " "
					i = i + 1
				p1com =  hold.rstrip()
			
			# Defining P2 Comments
			hold=""
			i=len(info)-3
			num = int(subtotal) - int(p1)
			while(info[i].isalpha and info[i] != str(num)):
				hold+=info[i] + " "
				i = i - 1
			hold1 = ""
			h=hold.split()
			for i in range(len(h)):
				hold1 += h[len(h)-1-i] + " "
			p2com = hold1.rstrip()
			p2 = str(int(subtotal)-int(p1))
			
			out = open(id+".txt","w")
			for i in range(len(arr)):
				arr[i] = arr[i].replace("<<NAME>>", name)
				arr[i] = arr[i].replace("<<COURSE>>", course)
				arr[i] = arr[i].replace("<<ID>>", id)
				arr[i] = arr[i].replace("<<P1>>", p1)
				arr[i] = arr[i].replace("<<TOTAL>>", total)
				arr[i] = arr[i].replace("<<SUBTOTAL>>", subtotal)
				arr[i] = arr[i].replace("<<LATEDEDUCTION>>", deduct)
				arr[i] = arr[i].replace("<<DUE>>", due)
				arr[i] = arr[i].replace("<<SUBMITTED>>", submitted)
				arr[i] = arr[i].replace("<<MINUTESLATE>>", late)
				arr[i] = arr[i].replace("<<P1COMMENTS>>", p1com)
				arr[i] = arr[i].replace("<<P2COMMENTS>>", p2com)
				arr[i] = arr[i].replace("<<P2>>", p2)
				out.write(arr[i])
			for u in range(len(held)):
				arr[u] = held[u]

elif(tsv == "b.tsv"):
	with open(tmp, "rt") as outline, open(tsv, "rt") as file:
		arr = outline.readlines()
		hold = []
		
		for i in range(len(arr)):
			hold.append(arr[i])
		
		lines = file.readlines()
		for j in range(len(lines)-1):
			identity = indi(j)
			out = open(identity[0]+".txt", "w")
			for i in range(len(arr)):
				arr[i] = arr[i].replace("<<ID>>", identity[0])
				arr[i] = arr[i].replace("<<NAME>>", identity[1]+" "+identity[2])
				arr[i] = arr[i].replace("<<ID2>>", identity[3])
				arr[i] = arr[i].replace("<<COURSE>>", identity[4])
				out.write(arr[i])
			for u in range(len(hold)):
				arr[u] = hold[u]

elif(tsv == "c.tsv" or tsv == "d.tsv"):
	with open(tmp, "rt") as outline, open(tsv, "rt") as file:
		arr = outline.readlines()
		hold = []
		
		for i in range(len(arr)):
			hold.append(arr[i])
		
		lines = file.readlines()
		for j in range(len(lines)-1):
			identity = indi(j)
			out = open(identity[0]+".txt", "w")
			for i in range(len(arr)):
				arr[i] = arr[i].replace("<<ID>>", identity[0])
				arr[i] = arr[i].replace("<<NAME>>", identity[1]+" "+identity[2])
				arr[i] = arr[i].replace("<<ID2>>", " ")
				arr[i] = arr[i].replace("<<COURSE>>", identity[3])
				out.write(arr[i])
			for u in range(len(hold)):
				arr[u] = hold[u]
				
elif(tsv == "e.tsv" or tsv == "f.tsv"):
	with open(tmp, "rt") as outline, open(tsv, "rt") as file:
		arr = outline.readlines()
		hold = []
		
		for i in range(len(arr)):
			hold.append(arr[i])
		
		lines = file.readlines()
		for j in range(len(lines)-1):
			identity = indi(j)
			out = open(identity[0]+".txt", "w")
			for i in range(len(arr)):
				arr[i] = arr[i].replace("<<LEFT>>", identity[1])
				arr[i] = arr[i].replace("<<STUFF>>", identity[2])
				arr[i] = arr[i].replace("<<RIGHT>>", identity[3])
				out.write(arr[i])
			for u in range(len(hold)):
				arr[u] = hold[u]