// hw4.cs
using System;
using System.IO;

namespace hw4{
	public class Test{
		public static void Main(string[] args){
			string tsv = args[0];
			string tmp = args[1];
            StreamReader sr = new StreamReader(tsv);
			StreamReader jr = new StreamReader(tmp);
			String ul = jr.ReadToEnd();
			String ln = ul;
                String line = sr.ReadToEnd();
				line = line.Replace("\n","\t");
				string[] words = line.Split('\t');
				
				if(tsv == "a.tsv"){
					for(int j = 1; j < 5; j++){
						ln = ln.Replace("<<NAME>>", words[13*j]);
						ln = ln.Replace("<<ID>>", words[13*j+1]);
						ln = ln.Replace("<<COURSE>>", words[13*j+2]);
						ln = ln.Replace("<<TOTAL>>", words[13*j+12]);
						ln = ln.Replace("<<SUBTOTAL>>", words[13*j+11]);
						ln = ln.Replace("<<LATEDEDUCTION>>", words[13*j+6]);
						ln = ln.Replace("<<DUE>>", words[13*3+3]);
						ln = ln.Replace("<<SUBMITTED>>", words[13*j+4]);
						ln = ln.Replace("<<MINUTESLATE>>", words[13*j+5]);
						ln = ln.Replace("<<P1>>", words[13*j+7]);
						ln = ln.Replace("<<P2>>", words[13*j+9]);
						ln = ln.Replace("<<P1COMMENTS>>", words[13*j+8]);
						ln = ln.Replace("<<P2COMMENTS>>", words[13*j+10]);
						string fileName = words[13*j+1];
						File.WriteAllText(Directory.GetCurrentDirectory()+"\\"+fileName+".txt", ln);
						File.WriteAllText(Directory.GetCurrentDirectory()+"/"+fileName+".txt", ln);
						ln = ul;
					}
				}
				
				else if(tsv == "b.tsv" || tsv == "d.tsv" || tsv == "c.tsv"){
					for(int j = 1; j < 5; j++){
						ln = ln.Replace("<<ID>>", words[4*j]);
						ln = ln.Replace("<<NAME>>", words[4*j+1]);
						ln = ln.Replace("<<ID2>>", words[4*j+2]);
						ln = ln.Replace("<<COURSE>>", words[4*j+3]);
						string fileName = words[4*j];
						File.WriteAllText(Directory.GetCurrentDirectory()+"\\"+fileName+".txt", ln);
						File.WriteAllText(Directory.GetCurrentDirectory()+"/"+fileName+".txt", ln);
						ln = ul;
					}
				}
				
				else if(tsv == "e.tsv"){
					for(int j = 1; j < 3; j++){
						ln = ln.Replace("<<LEFT>>", words[4*j+1]);
						ln = ln.Replace("<<STUFF>>", words[4*j+2]);
						ln = ln.Replace("<<RIGHT>>", words[4*j+3]);
						string fileName = words[4*j];
						File.WriteAllText(Directory.GetCurrentDirectory()+"\\"+fileName+".txt", ln);
						File.WriteAllText(Directory.GetCurrentDirectory()+"/"+fileName+".txt", ln);
						ln = ul;
					}
				}
				else{
					for(int j = 1; j < 3; j++){
						ln = ln.Replace("<<LEFT>>", words[5*j+1]);
						ln = ln.Replace("<<STUFF>>", words[5*j+2]);	
						ln = ln.Replace("<<RIGHT>>", words[5*j+3]);
						string fileName = words[5*j];
						File.WriteAllText(Directory.GetCurrentDirectory()+"\\"+fileName+".txt", ln);
						File.WriteAllText(Directory.GetCurrentDirectory()+"/"+fileName+".txt", ln);
						ln = ul;
					}
				}
		}
	}
}