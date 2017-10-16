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
	private ArrayList vertexList;//存储点的链表
    public int[][] edges;//邻接矩阵，用来存储边权
    private int numOfEdges;//边的数目
    private int numOfVexs;//点的数目
    public String vexs[];
    private static final int INF = 2000;   // 最大值
    public graph(int n,String[] vex) {
        //初始化矩阵，一维数组，和边的数目
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
    //得到结点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }
    //得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }
    //返回结点i的数据
    public Object getValueByIndex(int i) {
        return vertexList.get(i);
    }
    //返回v1,v2的权值
    public int getWeight(int v1,int v2) {
        return edges[v1][v2];
    }
    //插入结点
    public void insertVertex(Object vertex) {
        vertexList.add(vertexList.size(),vertex);
    }
    //插入结点
    public void insertEdge(int v1,int v2,int weight) {
    	if(edges[v1][v2]==INF)
    		edges[v1][v2]=weight;
    	else
    		edges[v1][v2]++;
        numOfEdges++;
    }
    //删除结点
    public void deleteEdge(int v1,int v2) {
        edges[v1][v2]=0;
        numOfEdges--;
    }
    //得到第一个邻接结点的下标
    public int getFirstNeighbor(int index) {
        for(int j=0;j<vertexList.size();j++) {
            if (edges[index][j]>0 && edges[index][j]!=INF ) {
                return j;
            }
        }
        return -1;
    }
    //根据前一个邻接结点的下标来取得下一个邻接结点
    public int getNextNeighbor(int v1,int v2) {
    	if (v1!=1000 && v2!=1000){
        for (int j=v2+1;j<vertexList.size();j++) {
            if (edges[v1][j]>0 && edges[v1][j]!= INF ) {
                return j;
            }
        }}
        return -1;
    }  
    //展示有向图======================================================================================
    public void showDirectedGraph(int n) {
        System.out.printf("生成的有向图表示：\n");
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < n; j++) {
            System.out.print(edges[i][j]);
            System.out.print(" ");
            }
            System.out.printf("\n");
        }
    }
    //得到位置
    public int getPosition(String word) {
    	for(int i = 0;i<vexs.length;i++)
    		if(vexs[i].equals(word))
    			return i;
    	return -1;
    }
    //获得邻接矩阵
    public int[][] getEdges() {
             return edges;
    }
 //=============================================================================================
    //查询桥接词
    public String queryBridgeWords(String word1, String word2){  //返回string situation
    	int p1 = getPosition(word1);
    	int p2 = getPosition(word2);
    	if(p1== -1 && p2 == -1)
    		return("situation1");
    	else if (p1== -1 && p2 != -1)
    		return("situation2");
    	else if (p1!= -1 && p2 == -1)
    		return("situation3");
    	else{
    		int first = getFirstNeighbor(p1);    //找到word1的第一个邻接点
    		int[] next = new int[vexs.length];   // next数组存放 word1的邻接点
    		int num = 1;    //next数组下标
    		next[0] =  first;   // 把第一个临界点放到数组
    		for(int i = 1;i<vexs.length;i++){  //数组从第二个开始
    			next[i] = 1000;
    			if(getNextNeighbor(p1,next[num-1])!= -1){  //如果存在 
    			     next[num] = getNextNeighbor(p1,next[num-1]); //word1 和 num-1的下一个邻接点
    		         num++;
    			}
    		}
    		int sum = -1;    // bridge数组下标
    		String[] bridge = new String[num];   //bridge存放所有满足条件的点
           	String ans = "";    //所有符合条件点的拼接字符串
           	
    		for(int i = 0;i<bridge.length;i++){
    			bridge[i] = "";  //初始化一下
   			    int nextfirst = getFirstNeighbor(next[i]);  //nextfirst 为next[i]的第一个邻接点
                int[] nextnext = new int[vexs.length];      // next[i]的邻接点数组nextnext
                nextnext[0] = nextfirst;  //nextfirst为第一个
                int nextnum = 1;   //nextnext数组下标
                for(int j = 1;j<vexs.length;j++){  //数组从第二个开始
                	nextnext[j]= 1000;
                	if(getNextNeighbor(next[j],nextnext[nextnum-1])!= -1){  //如果存在
       			     nextnext[nextnum] = getNextNeighbor(next[j],nextnext[nextnum-1]); //下一个邻接点
       			     nextnum++;}
                }
                
                for(int k = 0;k<nextnext.length;k++){
                	if (nextnext[k] == p2){
                		sum++;//个数加一
                		bridge[sum] = vexs[next[i]];     //中间点加入bridge
                    }	
    		    }  
            }  
    		
            if (sum == -1)   //如果个数为0
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
//根据bridge word生成新文本
    public String generateNewText(String inputText){
    	inputText = inputText.replaceAll("[\\p{Punct}\\p{Space}]+", " ");
    	inputText = inputText.toLowerCase();
    	String[] textArray = inputText.split(" ");//输入文本分割	
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
 //计算最短路径=====================================================================================
    public String printPath(int[][] path,int from, int to) {
    	  String shortText = "";
          while(path[from][to]!=from && path[from][to]!=-1){
        	   shortText = shortText + vexs[path[from][to]]+" ";
               to = path[from][to];
           }  
          return shortText;
     }     
     //path -- 路径。path[i][j]=k表示，"顶点i"到"顶点j"的最短路径会经过顶点k。
     //dist -- 长度数组。即，dist[i][j]=sum表示，"顶点i"到"顶点j"的最短路径的长度是sum。
    
    public String calcShortestPath(String word1, String word2) {
        int[][] path = new int[numOfVexs][numOfVexs];
        int[][] dist = new int[numOfVexs][numOfVexs];
        int p1 = getPosition(word1);// word1位置
        int p2 = getPosition(word2);//word2位置
        // 初始化
        for (int i = 0; i < numOfVexs; i++) {
            for (int j = 0; j < numOfVexs; j++) {
                dist[i][j] = edges[i][j];    // "顶点i"到"顶点j"的路径长度为"i到j的权值"。
                path[i][j] = -1;                // "顶点i"到"顶点j"的最短路径是经过顶点j。
            }
        }
     // 计算最短路径
        for (int k = 0; k < numOfVexs; k++) {
            for (int i = 0; i <numOfVexs; i++) {
                for (int j = 0; j < numOfVexs; j++) {
                    // 如果经过下标为k顶点路径比原两点间路径更短，则更新dist[i][j]和path[i][j]
                    int tmp = (dist[i][k]==INF || dist[k][j]==INF) ? INF : (dist[i][k] + dist[k][j]);
                    if (dist[i][j] > tmp) {
                        // "i到j最短路径"对应的值设，为更小的一个(即经过k)
                        dist[i][j] = tmp;
                        // "i到j最短路径"对应的路径，经过k
                        path[i][j] = k;
                    }
                }
            }
        }
        String shortText = printPath(path,p1,p2);
        return shortText+dist[p1][p2];
    }
 //随机游走========================================================================================
    public int[] allNeighbor(int p){  //返回位置为p顶点的所有下一个点
    	
    	int first = getFirstNeighbor(p);    //找到第一个邻接点
		int[] next = new int[vexs.length];   // next数组存放 p的邻接点
		int num = 1;    //next数组下标
		next[0] =  first;   // 把第一个临界点放到数组
		for(int i = 1;i<vexs.length;i++){  //数组从第二个开始
			next[i] = 1000;
			if(getNextNeighbor(p,next[num-1])!= -1){  //如果存在 
			     next[num] = getNextNeighbor(p,next[num-1]); //p和 num-1的下一个邻接点
		         num++;
			}
		}
		return next;
    }
    
    public String randomWalk(){    //无出度或第二次拜访时停止
    	System.out.println("请输入随机游走的起点：");
    	Scanner sc1 = new Scanner(System.in);
    	String start = sc1.nextLine();
    	String randomText = "";
    	String[] randomArray = new String[numOfEdges]; // 路径点的数组
    	for(int i = 0;i<numOfEdges;i++){   //初始化
    		randomArray[i] = "";
    	}
    	randomText =randomText + start + " ";   //把起点放到randomtext开头
    	int sp = getPosition(start);            //获取起点位置
    	int randomflag = sp;                    //循环因子
    	int arraynum = 0;                       // randomArray数组的下标'
    	sc1.close();
    	if (sp == -1)                           //第一种情况，起点不存在
    		return "condition";
    	
    	for(int i = 0;i<numOfEdges;i++){
    		int[] neighbor = allNeighbor(randomflag);   //获取因子的所有下一个临界点
    		int num1000 = 0;
    		for(int j = 0;j<vexs.length;j++){
    			if(neighbor[j]==1000){
    				num1000++;}
    		}
    		int rf = randomflag;
            if(neighbor[0]==-1){                        // 第二种情况如果第一个临界点为-1，即没有下一个临界点
            	break;}                    // 停止
            else{
            	Random random = new Random();
    		    int s = Math.abs(random.nextInt()%(neighbor.length-num1000));  //获得随机数
    		    randomflag = neighbor[s];//获得下一个因子
  		        int[] pos = new int[numOfEdges];// 存放randomflag在randomArray数组中位置的数组position
  		        int posnum = 0;
		        for(int j = 0;j<numOfEdges;j++){
		        	if(randomArray[j].equals(vexs[randomflag])){
		        		pos[posnum] = j;
		        		posnum++;
		        	}
		        }
		        for(int k = 0;k<numOfEdges;k++){
		        	if(pos[k]!=0 && vexs[rf].equals(randomArray[pos[k]-1])){//如果这个因子的前一个节点为上一个因子
		        		randomText =randomText +vexs[neighbor[s]] + " ";  //加入字符串
		        		return randomText;                           //情况三 重复
		        	}
		        }
    		    randomArray[arraynum] = vexs[neighbor[s]];        //加入数组
    		    arraynum++;
    		    randomText =randomText +vexs[neighbor[s]] + " ";  //加入字符串
    		    } 
            }
    	return randomText;
    }
 //主程序==========================================================================================
	public  static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("输入文件路径：");   //     D:\\workspace\\test.txt
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
		String strArray[] = str.split(" ");     //文件单词分割成功
			
			
	    int cf =0;  //重复单词的个数
		String[] temp =  new String[strArray.length];  //temp中间变量
		for(int i=0;i<temp.length;i++){
			temp[i] = strArray[i];      // 复制strArray
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
		//建立图
        graph pG = new graph(cf,vex);
		for(String x:vex) {
            pG.insertVertex(x);//插入结点
        }
        //插入边
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
		
		// 展示有向图===================================================================
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
        			NewFile.write(vex[i]+" -> "+vex[j]+" [ label = "+edgeArray[i][j]+" ]"+';'+"\r\n\t");//写入dot文件
        	}
        }
        NewFile.write('}');
        NewFile.close();
        // 查询桥接词bridge word========================================================
        System.out.println("请按先后顺序输入你想查询的两个节点：");

        System.out.println("输入word1：");
        String word1 = sc.nextLine();
        System.out.println("输入word2：");
        String word2 = sc.nextLine();
        if(pG.queryBridgeWords(word1, word2).equals("situation1"))
		       System.out.printf("No “%s” and “%s” in the graph!",word1,word2);
		else if(pG.queryBridgeWords(word1, word2).equals("situation2"))
			   System.out.printf("No “%s” in the graph!",word1);
		else if(pG.queryBridgeWords(word1, word2).equals("situation3"))
			   System.out.printf("No “%s” in the graph!",word2);
		else if(pG.queryBridgeWords(word1, word2).equals("situation4")) 
			   System.out.printf("No bridge words from “%s” to “%s” !",word1,word2);
		else{
			 String bridgeword = pG.queryBridgeWords(word1, word2);
			 String[] bridgeArray = bridgeword.split(" ");
			 System.out.printf("The bridge words from “%s” to “%s” is:",word1,word2);
			 for(int i = 0;i<bridgeArray.length;i++)
				 System.out.printf(bridgeArray[i]+",");
		} 
        System.out.printf("\n");
        //根据bridge word 生成新文本==============================================================================================================
        System.out.println("请输入新文本："); 
        String text = sc.nextLine();
        String newText = pG.generateNewText(text);
        System.out.println(newText);
        //计算两个单词之间的最短路径===============================================================================================================
        // 两个固定点
        System.out.println("请按先后顺序输入你想查询的两个节点：");
        System.out.println("输入word1：");
        String word3 = sc.nextLine();
        System.out.println("输入word2：");
        String word4 = sc.nextLine();
        
        String shortword = pG.calcShortestPath(word3, word4);
		String[] shortArray = shortword.split(" ");
		System.out.printf("The shortest path between “%s” and “%s” is %s ,include: %s",word3,word4,shortArray[shortArray.length-1],word3);
		for(int i = shortArray.length-2;i>=0;i--)
			System.out.printf(" -> "+shortArray[i]);
		System.out.println(" -> "+word4);
        //一个固定点的所有最短路径
        System.out.println("请按输入你想查询最短路径的节点：");
        System.out.println("输入word：");
        String word5 = sc.nextLine();
        
        for(int i = 0;i<cf;i++){
        	String shortword2 = pG.calcShortestPath(word5, vex[i]);
    		String[] shortArray2 = shortword2.split(" ");
    		System.out.printf("The shortest path between “%s” and “%s” is %s ,include: %s",word5,vex[i],shortArray2[shortArray2.length-1],word5);
    		for(int j = shortArray2.length-2;j>=0;j--)
    			System.out.printf(" -> "+shortArray2[j]);
    		System.out.println(" -> "+vex[i]);
        }
        //随机游走==================================================================================================
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