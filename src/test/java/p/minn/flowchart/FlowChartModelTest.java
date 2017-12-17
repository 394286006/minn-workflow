package p.minn.flowchart;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import p.minn.workflow.entity.ProcessDefinition;
import p.minn.workflow.utils.GojsTransform;
/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 
 * 
 */
public class FlowChartModelTest {

	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void easyTest() {
		String modealData="{ \"class\": \"go.GraphLinksModel\",\n" + 
				"  \"linkFromPortIdProperty\": \"fromPort\",\n" + 
				"  \"linkToPortIdProperty\": \"toPort\",\n" + 
				"  \"modelData\": {\"position\":\"-25 -78\"},\n" + 
				"  \"nodeDataArray\": [ \n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@\", \"loc\":\"230 230\"},\n" + 
				"{\"code\":\"D002\", \"children\":[], \"pid\":-1, \"id\":2, \"text\":\"软件部\", \"sort\":2, \"state\":{\"selected\":false}, \"rid\":2, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"2@D002@\", \"loc\":\"110 320\"},\n" + 
				"{\"code\":\"D003\", \"children\":[], \"pid\":-1, \"id\":3, \"text\":\"财务部\", \"sort\":3, \"state\":{\"selected\":false}, \"rid\":3, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"3@D003@\", \"loc\":\"340 300\"},\n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@2\", \"loc\":\"240 410\"},\n" + 
				"{\"code\":\"N006\", \"gid\":338, \"children\":[], \"pid\":-1, \"id\":7, \"sort\":\"6\", \"text\":\"请假环节\", \"state\":{\"selected\":false}, \"url\":\"node6\", \"selected\":false, \"key\":\"7@N006@\", \"loc\":\"220 130\"}\n" + 
				" ],\n" + 
				"  \"linkDataArray\": [ \n" + 
				"{\"from\":\"2@D002@\", \"to\":\"1@D001@\", \"fromPort\":\"\", \"toPort\":\"L\", \"points\":[110,303.0622756958008,110,293.0622756958008,110,229.99999999999997,142.17501449584955,229.99999999999997,174.35002899169913,229.99999999999997,184.35002899169913,229.99999999999997]},\n" + 
				"{\"from\":\"3@D003@\", \"to\":\"1@D001@\", \"fromPort\":\"L\", \"toPort\":\"R\", \"points\":[309.01001739501953,300,299.01001739501953,300,292.3299942016601,300,292.3299942016601,229.99999999999997,285.64997100830067,229.99999999999997,275.64997100830067,229.99999999999997]},\n" + 
				"{\"from\":\"1@D001@2\", \"to\":\"3@D003@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[240,393.0622756958008,240,383.0622756958008,240,355,340,355,340,326.9377243041992,340,316.9377243041992]},\n" + 
				"{\"from\":\"1@D001@2\", \"to\":\"2@D002@\", \"fromPort\":\"L\", \"toPort\":\"\", \"points\":[194.35002899169922,410,184.35002899169922,410,110,410,110,378.46886215209963,110,346.9377243041992,110,336.9377243041992]},\n" + 
				"{\"from\":\"1@D001@\", \"to\":\"7@N006@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[229.99999999999991,213.06227569580076,229.99999999999991,203.06227569580076,229.99999999999991,180,220,180,220,156.9377243041992,220,146.9377243041992]}\n" + 
				" ]}";
		
		List<ProcessDefinition> rs=GojsTransform.transform(1,"108_1", modealData);
		
		System.out.println("insert size:"+rs.size());
		for(ProcessDefinition pd:rs) {
			System.out.println("pid:"+pd.getPId()+",id:"+pd.getId());
		}
	}
	
	@Test
	public void complexTest() {
		String modealData="{ \"class\": \"go.GraphLinksModel\",\n" + 
				"  \"linkFromPortIdProperty\": \"fromPort\",\n" + 
				"  \"linkToPortIdProperty\": \"toPort\",\n" + 
				"  \"modelData\": {\"position\":\"7.350028991699219 -99\"},\n" + 
				"  \"nodeDataArray\": [ \n" + 
				"{\"code\":\"N003\", \"gid\":331, \"children\":[], \"pid\":-1, \"id\":4, \"sort\":\"3\", \"text\":\"环节3\", \"state\":{\"selected\":false}, \"url\":\"node3\", \"selected\":false, \"key\":\"4@N003@\", \"loc\":\"70 70\"},\n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@\", \"loc\":\"60 170\"},\n" + 
				"{\"code\":\"D002\", \"children\":[], \"pid\":-1, \"id\":2, \"text\":\"软件部\", \"sort\":2, \"state\":{\"selected\":false}, \"rid\":2, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"2@D002@\", \"loc\":\"60 270\"},\n" + 
				"{\"code\":\"N004\", \"gid\":332, \"children\":[], \"pid\":-1, \"id\":5, \"sort\":\"4\", \"text\":\"环节4\", \"state\":{\"selected\":false}, \"url\":\"mode4\", \"selected\":false, \"key\":\"5@N004@\", \"loc\":\"230 70\"},\n" + 
				"{\"code\":\"D002\", \"children\":[], \"pid\":-1, \"id\":2, \"text\":\"软件部\", \"sort\":2, \"state\":{\"selected\":false}, \"rid\":2, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"2@D002@2\", \"loc\":\"200 160\"},\n" + 
				"{\"code\":\"D003\", \"children\":[], \"pid\":-1, \"id\":3, \"text\":\"财务部\", \"sort\":3, \"state\":{\"selected\":false}, \"rid\":3, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"3@D003@\", \"loc\":\"220 260\"},\n" + 
				"{\"code\":\"N005\", \"gid\":333, \"children\":[], \"pid\":-1, \"id\":6, \"sort\":\"5\", \"text\":\"环节5\", \"state\":{\"selected\":false}, \"url\":\"node5\", \"selected\":false, \"key\":\"6@N005@\", \"loc\":\"370 70\"},\n" + 
				"{\"code\":\"D004\", \"children\":[], \"pid\":-1, \"id\":4, \"text\":\"商务部\", \"sort\":4, \"state\":{\"selected\":false}, \"rid\":4, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"4@D004@\", \"loc\":\"360 160\"},\n" + 
				"{\"code\":\"D003\", \"children\":[], \"pid\":-1, \"id\":3, \"text\":\"财务部\", \"sort\":3, \"state\":{\"selected\":false}, \"rid\":3, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"3@D003@2\", \"loc\":\"360 240\"},\n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@2\", \"loc\":\"360 320\"}\n" + 
				" ],\n" + 
				"  \"linkDataArray\": [ \n" + 
				"{\"from\":\"2@D002@\", \"to\":\"1@D001@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[60,253.0622756958008,60,243.0622756958008,60,220,60,220,60,196.93772430419924,60,186.93772430419924]},\n" + 
				"{\"from\":\"1@D001@\", \"to\":\"4@N003@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[60,153.0622756958008,60,143.0622756958008,60,120,70,120,70,96.93772430419921,70,86.93772430419921]},\n" + 
				"{\"from\":\"2@D002@2\", \"to\":\"5@N004@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[200,143.0622756958008,200,133.0622756958008,200,115,230,115,230,96.93772430419921,230,86.93772430419921]},\n" + 
				"{\"from\":\"3@D003@\", \"to\":\"2@D002@2\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[220,243.0622756958008,220,233.0622756958008,220,210,200,210,200,186.93772430419924,200,176.93772430419924]},\n" + 
				"{\"from\":\"3@D003@2\", \"to\":\"4@D004@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[360,223.0622756958008,360,213.0622756958008,360,200,360,200,360,186.93772430419924,360,176.93772430419924]},\n" + 
				"{\"from\":\"1@D001@2\", \"to\":\"3@D003@2\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[360,303.0622756958008,360,293.0622756958008,360,280,360,280,360,266.9377243041992,360,256.9377243041992]},\n" + 
				"{\"from\":\"4@D004@\", \"to\":\"6@N005@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[360,143.0622756958008,360,133.0622756958008,360,115,370,115,370,96.93772430419921,370,86.93772430419921]}\n" + 
				" ]}";
		List<ProcessDefinition> rs=GojsTransform.transform(1,"108_2", modealData);
		
		System.out.println("insert size:"+rs.size());
		for(ProcessDefinition pd:rs) {
			System.out.println("pid:"+pd.getPId()+",id:"+pd.getId());
		}

	}
	
	
}
