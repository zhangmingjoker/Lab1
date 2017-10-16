import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
//==========================================================================================
//Where
//I
//Make
//Difference
public class graph {
	private ArrayList vertexList;//�洢�������
    public int[][] edges;//�ڽӾ��������洢��Ȩ
    private int numOfEdges;//�ߵ���Ŀ
    private int numOfVexs;//�����Ŀ
    public String vexs[];
    private static final int INF = 2000;   // ���ֵ
    public graph(int n,String[] vex) {
        //��ʼ������һά���飬�ͱߵ���Ŀ
        edges=new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i==j)
                	edges[i][j] = 0;
                else
                	edges[i][j] = INF;
            }
        }
        numOfVexs = n;
        vertexList=new ArrayList(n);
        numOfEdges=0;
        vexs = vex;
    }
    //�õ����ĸ���
    public int getNumOfVertex() {
        return vertexList.size();
    }
    //�õ��ߵ���Ŀ
    public int getNumOfEdges() {
        return numOfEdges;
    }
    //���ؽ��i������
    public Object getValueByIndex(int i) {
        return vertexList.get(i);
    }
    //����v1,v2��Ȩֵ
    public int getWeight(int v1,int v2) {
        return edges[v1][v2];
    }
    //������
    public void insertVertex(Object vertex) {
        vertexList.add(vertexList.size(),vertex);
    }
    //������
    public void insertEdge(int v1,int v2,int weight) {
    	if(edges[v1][v2]==INF)
    		edges[v1][v2]=weight;
    	else
    		edges[v1][v2]++;
        numOfEdges++;
    }
    //ɾ�����
    public void deleteEdge(int v1,int v2) {
        edges[v1][v2]=0;
        numOfEdges--;
    }
    //�õ���һ���ڽӽ����±�
    public int getFirstNeighbor(int index) {
        for(int j=0;j<vertexList.size();j++) {
            if (edges[index][j]>0 && edges[index][j]!=INF ) {
                return j;
            }
        }
        return -1;
    }
    //����ǰһ���ڽӽ����±���ȡ����һ���ڽӽ��
    public int getNextNeighbor(int v1,int v2) {
    	if (v1!=1000 && v2!=1000){
        for (int j=v2+1;j<vertexList.size();j++) {
            if (edges[v1][j]>0 && edges[v1][j]!= INF ) {
                return j;
            }
        }}
        return -1;
    }  
    //չʾ����ͼ======================================================================================
    public void showDirectedGraph(int n) {
        System.out.printf("���ɵ�����ͼ��ʾ��\n");
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < n; j++) {
            System.out.print(edges[i][j]);
            System.out.print(" ");
            }
            System.out.printf("\n");
        }
    }
    //�õ�λ��
    public int getPosition(String word) {
    	for(int i = 0;i<vexs.length;i++)
    		if(vexs[i].equals(word))
    			return i;
    	return -1;
    }
    //����ڽӾ���
    public int[][] getEdges() {
             return edges;
    }
 //=============================================================================================
    //��ѯ�ŽӴ�
    public String queryBridgeWords(String word1, String word2){  //����string situation
    	int p1 = getPosition(word1);
    	int p2 = getPosition(word2);
    	if(p1== -1 && p2 == -1)
    		return("situation1");
    	else if (p1== -1 && p2 != -1)
    		return("situation2");
    	else if (p1!= -1 && p2 == -1)
    		return("situation3");
    	else{
    		int first = getFirstNeighbor(p1);    //�ҵ�word1�ĵ�һ���ڽӵ�
    		int[] next = new int[vexs.length];   // next������ word1���ڽӵ�
    		int num = 1;    //next�����±�
    		next[0] =  first;   // �ѵ�һ���ٽ��ŵ�����
    		for(int i = 1;i<vexs.length;i++){  //����ӵڶ�����ʼ
    			next[i] = 1000;
    			if(getNextNeighbor(p1,next[num-1])!= -1){  //������� 
    			     next[num] = getNextNeighbor(p1,next[num-1]); //word1 �� num-1����һ���ڽӵ�
    		         num++;
    			}
    		}
    		int sum = -1;    // bridge�����±�
    		String[] bridge = new String[num];   //bridge����������������ĵ�
           	String ans = "";    //���з����������ƴ���ַ���
           	
    		for(int i = 0;i<bridge.length;i++){
    			bridge[i] = "";  //��ʼ��һ��
   			    int nextfirst = getFirstNeighbor(next[i]);  //nextfirst Ϊnext[i]�ĵ�һ���ڽӵ�
                int[] nextnext = new int[vexs.length];      // next[i]���ڽӵ�����nextnext
                nextnext[0] = nextfirst;  //nextfirstΪ��һ��
                int nextnum = 1;   //nextnext�����±�
                for(int j = 1;j<vexs.length;j++){  //����ӵڶ�����ʼ
                	nextnext[j]= 1000;
                	if(getNextNeighbor(next[j],nextnext[nextnum-1])!= -1){  //�������
       			     nextnext[nextnum] = getNextNeighbor(next[j],nextnext[nextnum-1]); //��һ���ڽӵ�
       			     nextnum++;}
                }
                
                for(int k = 0;k<nextnext.length;k++){
                	if (nextnext[k] == p2){
                		sum++;//������һ
                		bridge[sum] = vexs[next[i]];     //�м�����bridge
                    }	
    		    }  
            }  
    		
            if (sum == -1)   //�������Ϊ0
                return("situation4");
            else{
                for(int x = 0;x<bridge.length;x++){
                	if(bridge[x].equals("")==false){
                		ans = ans + bridge[x];
                		ans = ans + " ";}
                	}
                return ans;
    	    }
            
    	}
    }
//����bridge word�������ı�
    public String generateNewText(String inputText){
    	inputText = inputText.replaceAll("[\\p{Punct}\\p{Space}]+", " ");
    	inputText = inputText.toLowerCase();
    	String[] textArray = inputText.split(" ");//�����ı��ָ�	
    	String newText = "";
    	for(int i = 0;i<textArray.length-1;i++){
    		if(queryBridgeWords(textArray[i],textArray[i+1]).equals("situation1")){
    			newText = newText +textArray[i]+" ";}	
    		else if(queryBridgeWords(textArray[i],textArray[i+1]).equals("situation2")){
    			newText = newText +textArray[i]+" ";}
    		else if(queryBridgeWords(textArray[i],textArray[i+1]).equals("situation3")){
    			newText = newText +textArray[i]+" ";}
    		else if(queryBridgeWords(textArray[i],textArray[i+1]).equals("situation4")){
    			newText = newText +textArray[i]+" ";}
    		else{
    			String[] bridgeArray = queryBridgeWords(textArray[i],textArray[i+1]).split(" ");
    			newText = newText +textArray[i]+" "+bridgeArray[0]+" ";
    		}
    	}
    	newText = newText+textArray[textArray.length-1];
    	
    	return newText;
    }
 //�������·��=====================================================================================
    public String printPath(int[][] path,int from, int to) {
    	  String shortText = "";
          while(path[from][to]!=from && path[from][to]!=-1){
        	   shortText = shortText + vexs[path[from][to]]+" ";
               to = path[from][to];
           }  
          return shortText;
     }     
     //path -- ·����path[i][j]=k��ʾ��"����i"��"����j"�����·���ᾭ������k��
     //dist -- �������顣����dist[i][j]=sum��ʾ��"����i"��"����j"�����·���ĳ�����sum��
    
    public String calcShortestPath(String word1, String word2) {
        int[][] path = new int[numOfVexs][numOfVexs];
        int[][] dist = new int[numOfVexs][numOfVexs];
        int p1 = getPosition(word1);// word1λ��
        int p2 = getPosition(word2);//word2λ��
        // ��ʼ��
        for (int i = 0; i < numOfVexs; i++) {
            for (int j = 0; j < numOfVexs; j++) {
                dist[i][j] = edges[i][j];    // "����i"��"����j"��·������Ϊ"i��j��Ȩֵ"��
                path[i][j] = -1;                // "����i"��"����j"�����·���Ǿ�������j��
            }
        }
     // �������·��
        for (int k = 0; k < numOfVexs; k++) {
            for (int i = 0; i <numOfVexs; i++) {
                for (int j = 0; j < numOfVexs; j++) {
                    // ��������±�Ϊk����·����ԭ�����·�����̣������dist[i][j]��path[i][j]
                    int tmp = (dist[i][k]==INF || dist[k][j]==INF) ? INF : (dist[i][k] + dist[k][j]);
                    if (dist[i][j] > tmp) {
                        // "i��j���·��"��Ӧ��ֵ�裬Ϊ��С��һ��(������k)
                        dist[i][j] = tmp;
                        // "i��j���·��"��Ӧ��·��������k
                        path[i][j] = k;
                    }
                }
            }
        }
        String shortText = printPath(path,p1,p2);
        return shortText+dist[p1][p2];
    }
 //�������========================================================================================
    public int[] allNeighbor(int p){  //����λ��Ϊp�����������һ����
    	
    	int first = getFirstNeighbor(p);    //�ҵ���һ���ڽӵ�
		int[] next = new int[vexs.length];   // next������ p���ڽӵ�
		int num = 1;    //next�����±�
		next[0] =  first;   // �ѵ�һ���ٽ��ŵ�����
		for(int i = 1;i<vexs.length;i++){  //����ӵڶ�����ʼ
			next[i] = 1000;
			if(getNextNeighbor(p,next[num-1])!= -1){  //������� 
			     next[num] = getNextNeighbor(p,next[num-1]); //p�� num-1����һ���ڽӵ�
		         num++;
			}
		}
		return next;
    }
    
    public String randomWalk(){    //�޳��Ȼ�ڶ��ΰݷ�ʱֹͣ
    	System.out.println("������������ߵ���㣺");
    	Scanner sc1 = new Scanner(System.in);
    	String start = sc1.nextLine();
    	String randomText = "";
    	String[] randomArray = new String[numOfEdges]; // ·���������
    	for(int i = 0;i<numOfEdges;i++){   //��ʼ��
    		randomArray[i] = "";
    	}
    	randomText =randomText + start + " ";   //�����ŵ�randomtext��ͷ
    	int sp = getPosition(start);            //��ȡ���λ��
    	int randomflag = sp;                    //ѭ������
    	int arraynum = 0;                       // randomArray������±�'
    	sc1.close();
    	if (sp == -1)                           //��һ���������㲻����
    		return "condition";
    	
    	for(int i = 0;i<numOfEdges;i++){
    		int[] neighbor = allNeighbor(randomflag);   //��ȡ���ӵ�������һ���ٽ��
    		int num1000 = 0;
    		for(int j = 0;j<vexs.length;j++){
    			if(neighbor[j]==1000){
    				num1000++;}
    		}
    		int rf = randomflag;
            if(neighbor[0]==-1){                        // �ڶ�����������һ���ٽ��Ϊ-1����û����һ���ٽ��
            	break;}                    // ֹͣ
            else{
            	Random random = new Random();
    		    int s = Math.abs(random.nextInt()%(neighbor.length-num1000));  //��������
    		    randomflag = neighbor[s];//�����һ������
  		        int[] pos = new int[numOfEdges];// ���randomflag��randomArray������λ�õ�����position
  		        int posnum = 0;
		        for(int j = 0;j<numOfEdges;j++){
		        	if(randomArray[j].equals(vexs[randomflag])){
		        		pos[posnum] = j;
		        		posnum++;
		        	}
		        }
		        for(int k = 0;k<numOfEdges;k++){
		        	if(pos[k]!=0 && vexs[rf].equals(randomArray[pos[k]-1])){//���������ӵ�ǰһ���ڵ�Ϊ��һ������
		        		randomText =randomText +vexs[neighbor[s]] + " ";  //�����ַ���
		        		return randomText;                           //����� �ظ�
		        	}
		        }
    		    randomArray[arraynum] = vexs[neighbor[s]];        //��������
    		    arraynum++;
    		    randomText =randomText +vexs[neighbor[s]] + " ";  //�����ַ���
    		    } 
            }
    	return randomText;
    }
 //������==========================================================================================
	public  static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("�����ļ�·����");   //     D:\\workspace\\test.txt
        String txtpath = sc.nextLine();
		File file = new File(txtpath);
		StringBuilder sb = new StringBuilder();
		String s ="";
		BufferedReader br = new BufferedReader(new FileReader(file));
		while( (s = br.readLine()) != null) {
			sb.append(s + "\n");
		}
		br.close();
		String str = sb.toString();
		str = str.replaceAll("[\\p{Punct}\\p{Space}]+", " ");
		str = str.toLowerCase();
		String strArray[] = str.split(" ");     //�ļ����ʷָ�ɹ�
			
			
	    int cf =0;  //�ظ����ʵĸ���
		String[] temp =  new String[strArray.length];  //temp�м����
		for(int i=0;i<temp.length;i++){
			temp[i] = strArray[i];      // ����strArray
		}
		for(int i=0;i<temp.length;i++){
			for(int j=i+1;j<temp.length;j++){
				if(temp[i].equals(temp[j])){
					temp[j]="";
				}
			}
		}
		for(int i=0;i<temp.length;i++){
			if(temp[i].equals("")==false)
				cf++;}
		String[] vex = new String[cf];
		int num = 0;
		for(int i=0;i<temp.length;i++){
			if(temp[i].equals(""))
				continue;
			else{
				vex[num]=temp[i];
			    num++;
			}
		}	
		//����ͼ
        graph pG = new graph(cf,vex);
		for(String x:vex) {
            pG.insertVertex(x);//������
        }
        //�����
		int first = 0;
		int last = 0;
		for(int i=0;i<strArray.length-1;i++){
		    for(int j = 0;j<vex.length;j++){
		    	if(vex[j].equals(strArray[i]))
		    		first = j;}
		    for(int k = 0;k<vex.length;k++){
		    	if(vex[k].equals(strArray[i+1]))
		    		last = k;}
			pG.insertEdge(first,last,1);
		}
		
		// չʾ����ͼ===================================================================
        pG.showDirectedGraph(cf); 
        
		int[][] edgeArray = pG.getEdges();
		File DotFile = new File("d:\\Graph1.dot");
        FileWriter NewFile = new FileWriter(DotFile);
        NewFile.write("digraph graph1{\r\n\tnode [shape=\"record\"];\r\n\t");
        for(int i=0;i<strArray.length;i++)
            NewFile.write(strArray[i]+";\r\n\t");
        for(int i=0;i<cf;i++)
        {
        	for(int j=0;j<cf;j++)
        	{
        		if(edgeArray[i][j]!=INF && edgeArray[i][j]!=0)
        			NewFile.write(vex[i]+" -> "+vex[j]+" [ label = "+edgeArray[i][j]+" ]"+';'+"\r\n\t");//д��dot�ļ�
        	}
        }
        NewFile.write('}');
        NewFile.close();
        // ��ѯ�ŽӴ�bridge word========================================================
        System.out.println("�밴�Ⱥ�˳�����������ѯ�������ڵ㣺");

        System.out.println("����word1��");
        String word1 = sc.nextLine();
        System.out.println("����word2��");
        String word2 = sc.nextLine();
        if(pG.queryBridgeWords(word1, word2).equals("situation1"))
		       System.out.printf("No ��%s�� and ��%s�� in the graph!",word1,word2);
		else if(pG.queryBridgeWords(word1, word2).equals("situation2"))
			   System.out.printf("No ��%s�� in the graph!",word1);
		else if(pG.queryBridgeWords(word1, word2).equals("situation3"))
			   System.out.printf("No ��%s�� in the graph!",word2);
		else if(pG.queryBridgeWords(word1, word2).equals("situation4")) 
			   System.out.printf("No bridge words from ��%s�� to ��%s�� !",word1,word2);
		else{
			 String bridgeword = pG.queryBridgeWords(word1, word2);
			 String[] bridgeArray = bridgeword.split(" ");
			 System.out.printf("The bridge words from ��%s�� to ��%s�� is:",word1,word2);
			 for(int i = 0;i<bridgeArray.length;i++)
				 System.out.printf(bridgeArray[i]+",");
		} 
        System.out.printf("\n");
        //����bridge word �������ı�==============================================================================================================
        System.out.println("���������ı���"); 
        String text = sc.nextLine();
        String newText = pG.generateNewText(text);
        System.out.println(newText);
        //������������֮������·��===============================================================================================================
        // �����̶���
        System.out.println("�밴�Ⱥ�˳�����������ѯ�������ڵ㣺");
        System.out.println("����word1��");
        String word3 = sc.nextLine();
        System.out.println("����word2��");
        String word4 = sc.nextLine();
        
        String shortword = pG.calcShortestPath(word3, word4);
		String[] shortArray = shortword.split(" ");
		System.out.printf("The shortest path between ��%s�� and ��%s�� is %s ,include: %s",word3,word4,shortArray[shortArray.length-1],word3);
		for(int i = shortArray.length-2;i>=0;i--)
			System.out.printf(" -> "+shortArray[i]);
		System.out.println(" -> "+word4);
        //һ���̶�����������·��
        System.out.println("�밴���������ѯ���·���Ľڵ㣺");
        System.out.println("����word��");
        String word5 = sc.nextLine();
        
        for(int i = 0;i<cf;i++){
        	String shortword2 = pG.calcShortestPath(word5, vex[i]);
    		String[] shortArray2 = shortword2.split(" ");
    		System.out.printf("The shortest path between ��%s�� and ��%s�� is %s ,include: %s",word5,vex[i],shortArray2[shortArray2.length-1],word5);
    		for(int j = shortArray2.length-2;j>=0;j--)
    			System.out.printf(" -> "+shortArray2[j]);
    		System.out.println(" -> "+vex[i]);
        }
        //�������==================================================================================================
        String randompath = pG.randomWalk();
        if(randompath.equals("condition")){
        	System.out.println("NO this word");}
        else{
        	String[] walkArray = randompath.split(" ");
    		System.out.printf("The random walk is :" );
    		for(int i = 0;i<walkArray.length-1;i++)
    			System.out.printf(walkArray[i]+" -> ");
    		System.out.printf(walkArray[walkArray.length-1]);
        }
        
        sc.close();  
	}
}