package net.Ken365X.Utils;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


/**
 * 
* @ProjectName gppos
* @ClassName SimHash 
* @Description TODO(�ж��ı����ƶȣ�ֻ֧������) 
* @author makang
* @date 2016-5-27 ����4:57:56 
* @version V1.0
 */
public class SimHash {
private String tokens;
    private BigInteger intSimHash;
    private String strSimHash;
    private int hashbits = 64;
    public SimHash(String tokens) throws IOException {
        this.tokens = tokens;
        this.intSimHash = this.simHash();
    }
    public SimHash(String tokens, int hashbits) throws IOException {
        this.tokens = tokens;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }
    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
    public BigInteger simHash() throws IOException {
        // ������������/����
        int[] v = new int[this.hashbits];
        // Ӣ�ķִ�
        // StringTokenizer stringTokens = new StringTokenizer(this.tokens);
        // while (stringTokens.hasMoreTokens()) {
        // String temp = stringTokens.nextToken();
        // }
        // 1�����ķִʣ��ִ������� IKAnalyzer3.2.8 ��������ʾʹ�ã��°� API �ѱ仯��
        StringReader reader = new StringReader(this.tokens);
        // ��Ϊtrueʱ���ִ����������ʳ��з�
        IKSegmenter ik = new IKSegmenter(reader, true);
        Lexeme lexeme = null;
        String word = null;
       // String temp = null;
        while ((lexeme = ik.next()) != null) {
            word = lexeme.getLexemeText();
            // ע��ͣ�ôʻᱻ�ɵ�
            // System.out.println(word);
            // 2����ÿһ���ִ�hashΪһ��̶����ȵ�����.���� 64bit ��һ������.
            BigInteger t = this.hash(word);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3������һ������Ϊ64����������(����Ҫ����64λ������ָ��,Ҳ��������������),
                // ��ÿһ���ִ�hash������н����ж�,�����1000...1,��ô����ĵ�һλ��ĩβһλ��1,
                // �м��62λ��һ,Ҳ����˵,��1��1,��0��1.һֱ�������еķִ�hash����ȫ���ж����.
                if (t.and(bitmask).signum() != 0) {
                    // �����Ǽ��������ĵ�������������������
                    // ����ʵ��ʹ������Ҫ +- Ȩ�أ������Ƶ�������Ǽ򵥵� +1/-1��
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }


        BigInteger fingerprint = new BigInteger("0");
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < this.hashbits; i++) {
            // 4��������������ж�,����0�ļ�Ϊ1,С�ڵ���0�ļ�Ϊ0,�õ�һ�� 64bit ������ָ��/ǩ��.
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                simHashBuffer.append("1");
            } else {
                simHashBuffer.append("0");
            }
        }
        this.strSimHash = simHashBuffer.toString();
        System.out.println(this.strSimHash + " length " + this.strSimHash.length());
        return fingerprint;
    }


    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }


    public int hammingDistance(SimHash other) {


        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int tot = 0;


        // ͳ��x�ж�����λ��Ϊ1�ĸ���
        // �������룬һ������������ȥ1����ô��������Ǹ�1�������Ǹ�1�����������ȫ�����ˣ�
        // �԰ɣ�Ȼ��n&(n-1)���൱�ڰѺ����������0��
        // ���ǿ�n�������ٴ������Ĳ�����OK�ˡ�


        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }


    public int getDistance(String str1, String str2) {
        int distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }


    public List<Object> subByDistance(SimHash simHash, int distance) {
        // �ֳɼ��������
        int numEach = this.hashbits / (distance + 1);
        List<Object> characters = new ArrayList<Object>();


        StringBuffer buffer = new StringBuffer();


       // int k = 0;
        for (int i = 0; i < this.intSimHash.bitLength(); i++) {
            // ���ҽ���������ָ����λʱ������ true
            boolean sr = simHash.intSimHash.testBit(i);


            if (sr) {
                buffer.append("1");
            } else {
                buffer.append("0");
            }


            if ((i + 1) % numEach == 0) {
                // ��������תΪBigInteger
                BigInteger eachValue = new BigInteger(buffer.toString(), 2);
                System.out.println("----" + eachValue);
                buffer.delete(0, buffer.length());
                characters.add(eachValue);
            }
        }


        return characters;
    }
    public BigInteger getIntSimHash() {
		return intSimHash;
	}
    public String getStrSimHash() {
		return strSimHash;
	}
    
    // ���ƶ�
 	public double getGensim(String a_paperHash, String z_paperHash){
 		int dis;
 		dis = 0;
 		for (int i = 0; i < a_paperHash.length(); i++) {
 			if(a_paperHash.charAt(i) == z_paperHash.charAt(i)){
 				dis++;
 			}
 		}
 		return (double)dis / a_paperHash.length();
 	}
    
    public static void main(String[] args) throws IOException {
        String s = "��ҵ����ʿ��Ϊ��������1.0��Ҫ������ֻ��ң�������2.0������ܺ�Լ������Ӧ���ڽ����г��С������������ܽ����ܡ����桢��١����ڵ������ʹ���ѵ㣬���в�������������һ������������ȥ���Ļ����ص��ʺ϶෽����ĳ��������ֻ�ǵ��߻�˫�߲����ֵ�Ͳ���������Ҫÿ���ڵ㶼ȥ�˶ԣ�����������Ҳ��������Щ��Ƶ���׵Ļ������������ǿ�����ǹ���͸���������ʺ϶�������˽Ҫ���ر�ߵĳ�����\r\n" + 
        		"    ����������������ĵ�һ��Ӧ�������������ܽ�����ڻ�������ʱȱ����ܵ����⡣�����������������ˣ����ڣ������İ�Ƥ����Ϊ��������Ϊ���ڼ�ܻ����ṩ��һ����������Ƶ����ݣ�ͨ���Ի����������������ݷ������ܹ��ȴ�ͳ������̸������ȷ�ؼ�ܽ���ҵ��\r\n" + 
        		"    ���ڷ�����������������Ϊ������������ĳɱ���Ч������,���������������ܽ��֧�����ʲ�����֤ȯ�ȶ��������ڵ�ʹ�㡣���磬ÿ���˺ŵ����ͽ��׼�¼���ǿ�׷�ٵģ�����һ�ʽ��׵��κ�һ�����ڶ��������������ߣ��⽫������߷�ϴǮ�����ȡ�����\r\n" + 
        		"������֧��Ϊ�����������������Դ������֣�ʹ���ڻ����ܸ��ô���������ɱ����߶�����Ϊ����ʵ��С��羳֧����������ʵ���ջݽ��ڡ����ڻ����ر��ǿ羳���ڻ�����Ķ��ˡ����㡢����ĳɱ��ϸߣ��漰�ܶ��ֹ����̣����������û��˺ͽ��ڻ�����̨ҵ��˵Ȳ����߰��ķ��ã�Ҳʹ��С��֧��ҵ�����Կ�չ��������������Ӧ�������ڽ��ͽ��ڻ�����Ķ��˳ɱ����������ĳɱ����������֧��ҵ��Ĵ���Ч�ʡ�\r\n" + 
        		"����ȥ�꣬���������Ƴ�΢�������������ƽ̨����Ҳ�ǹ����׸����������������е�����ҵ������Ӧ�ó������ſ�����Ϊ����ͳ�������ļ����ˡ�ģʽ��������δ�ܽ���ĳɱ������⣬��������������������֮�ء���󣬶��������̽�����������ƽ̨��ͨ���������������Ż�΢����ҵ���еĻ�����������̣�ʵ����׼ʵʱ���ˡ������ӪЧ�ʡ�������Ӫ�ɱ���Ŀ�ꡣ����Ŀǰ��ƽ̨�ȶ�����1��࣬��������ϣ���¼����ʵ���ױ����Ѵ�ǧ��������\r\n" + 
        		"�����ڹ��������׸�Ӧ�ó������ǹ��棬����һȺ���϶�ͯ���һ���ƿȻ�����������������ٽ�������ӿ���͸����������˵������������ƽ̨�����������ڻ������Ϲ�����һ��ר�������ʼ��ʽ���ʾ֡��û����ÿһ��Ǯ�����Ƕ�������һ���������������ͨ��������ƽ̨���ݣ�ÿ����һ���ڵ㣬���Ƕ������һ���ʴ�������͵��ܾ������ϡ��������Ա�֤�û����ÿһ��Ǯ����͸������׷�ݣ����Դ۸ĵġ�\r\n" + 
        		"��������Ʒ��ٷ��棬����������Ҳ�ܷ���һ��֮������������ܣ����Ͻ����������������������Ʒ��Դ�ϡ�Ŀǰ�����в������԰Ĵ����ǡ��������ĺ�����Ʒ�����̷ۣ���֧����ɨһɨ������֪���ǲ�����Ʒ��������ǰ¼����Ʒ��Ϣ��ͬ���ǣ����������ö�λ����ʦ�����������ɵ�������ɼ��ˡ���\r\n" + 
        		"����������ȷʵ�ܽ���ܶ������ʹ���ѵ㣬�������������п�Ϊ�����������������ܵģ�Ҳ�кܶ�����������������3.0���õĳ���������࣬�����Ὺ��һ��������ʱ����";
        SimHash hash1 = new SimHash(s, 64);
        System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
        // ���� �������� �� 3 ���ڵĸ���ǩ���� hash ֵ
        hash1.subByDistance(hash1, 3);


        // ɾ���׾仰���������������Ŵ�
        s = "��������ʲô��\r\n" + 
        		"�����ܽ�����ڡ����桢��ܡ���ٵȺܶ������ʹ���ѵ㣬���в�������������\r\n" + 
        		"�������ڷ����������������ĵ�һ��Ӧ���������������������ܽ��֧�����ʲ�����֤ȯ�ȶ��������ڵ�ʹ�㡣\r\n" + 
        		"������֧������Ϊ�������ڻ����ر��ǿ羳���ڻ�����Ķ��ˡ����㡢����ĳɱ��ϸߣ��漰�ܶ��ֹ����̣����������û��˺ͽ��ڻ�����̨ҵ��˵Ȳ����߰��ķ��ã�Ҳʹ��С��֧��ҵ�����Կ�չ��������������Ӧ�������ڽ��ͽ��ڻ�����Ķ��˳ɱ����������ĳɱ����������֧��ҵ��Ĵ���Ч�ʡ����⣬����������Ϊ֧����������ĳɱ���Ч�����ƣ�ʹ���ڻ����ܸ��ô���������ɱ����߶�����Ϊ����ʵ��С��羳֧����������ʵ���ջݽ��ڡ�\r\n" + 
        		"�������磬Ϊ������ڻ�������˳ɱ��ߵ����⣬2016��8�£�΢�����������Ϻ����������Ƴ�΢�������������ƽ̨����Ҳ�ǹ����׸����������������е�����ҵ������Ӧ�ó�����΢��������������ϯ�ܹ�ʦ�ſ�����Ϊ����ͳ�������ļ����ˡ�ģʽ��������δ�ܽ���ĳɱ������⣬��������������������֮�ء�����������С���ɳ����Ҳ��̽�����������ƽ̨��ͨ���������������Ż�΢����ҵ���еĻ�����������̣�ʵ����׼ʵʱ���ˡ������ӪЧ�ʡ�������Ӫ�ɱ���Ŀ�ꡣ����Ŀǰ��ƽ̨�ȶ�����1��࣬��������ϣ���¼����ʵ���ױ����Ѵ�ǧ��������\r\n" + 
        		"�����ڹ�����������������Ҳ���п�Ϊ�����Ͻ���漰���������׸�Ӧ�ó������ǹ��棬����һȺ���϶�ͯ���һ���ƿȻ�����������������ٽ�������ӿ���͸�������Ͻ������ʵ���Ҹ߼���Ʒר�Һ�����˵��������������ƽ̨�����������ڻ������Ϲ�����һ��ר�������ʼ��ʽ���ʾ֡��û����ÿһ��Ǯ�����Ƕ�������һ���������������ͨ��������ƽ̨���ݣ�ÿ����һ���ڵ㣬���Ƕ������һ���ʴ�������͵��ܾ������ϡ��������Ա�֤�û����ÿһ��Ǯ����͸������׷�ݡ����Դ۸ĵġ���\r\n" + 
        		"��������Ʒ��ٷ��棬�������������Դ������֡���������ܣ����Ͻ����������������������Ʒ��Դ�ϡ�Ŀǰ�����в������԰Ĵ����ǡ��������ĺ�����Ʒ�����̷ۣ���֧����ɨһɨ������֪���ǲ�����Ʒ��������ǰ�̼���¼����Ʒ��Ϣ��ͬ���ǣ����������ö�λ������ʦ�����������������ɵ�������ɼ��ˡ���\r\n" + 
        		"�������ڽ��ڼ�ܣ�����������Ҳ�ܷ���һ��֮����2017������������������ˣ����ڣ������ġ������������ײ�ƽ̨FISCO BCOS��Ƥ�顷��Ϊ��������Ϊ���ڼ�ܻ����ṩ��һ����������Ƶ����ݣ�ͨ���Ի����������������ݷ������ܹ��ȴ�ͳ������̸������ȷ�ؼ�ܽ���ҵ�����磬�ڷ�ϴǮ�����У�ÿ���˺ŵ����ͽ��׼�¼���ǿ�׷�ٵģ�����һ�ʽ��׵��κ�һ�����ڶ��������������ߣ��⽫������߷�ϴǮ�����ȡ�\r\n" + 
        		"������ҵ����ʿ��Ϊ��������1.0��Ҫ������ֻ��ң�������2.0������ܺ�Լ������Ӧ���ڽ����г��У�������3.0���õĳ���������࣬�����Ὺ��һ����������ʱ������\r\n" + 
        		"�����η���Ϊ��������ȷʵ�ܽ���ܶ������ʹ���ѵ㣬���������������ܵģ�Ҳ�кܶ�����������\r\n" + 
        		"�������磬����������ȥ���Ļ����ص��ʺ϶෽����ĳ��������ֻ�ǵ��߻�˫�߲����ֵ�Ͳ���������Ҫÿ���ڵ㶼ȥ�˶ԣ�����������Ҳ��������Щ��Ƶ���׵Ļ��\r\n" + 
        		"�������磬������ǿ�����ǹ���͸���������ʺ϶�������˽Ҫ���ر�ߵĳ�����";
        SimHash hash2 = new SimHash(s, 64);
        System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
        hash1.subByDistance(hash2, 3);


        // �׾�ǰ���һ�仰���������ĸ����Ŵ�
        s = "imhash�㷨��������һ�������������һ�� f λ��ǩ��ֵ��Ϊ�˳������㣬" 
                + "�����������һ���ĵ����������ϣ�ÿ��������һ����Ȩ�ء�"
                + "��ͳ����4�� hash �㷨ֻ����ԭʼ���ݾ������������ӳ��Ϊһ��ǩ��ֵ��" 
                + "ԭ������β����ж����3�൱��α����������㷨������������ǩ���������ȣ�"
                + "˵��ԭʼ������һ���� �� ������ȵģ��������ȣ�����˵��ԭʼ���ݲ�����⣬�����ṩ�κ���Ϣ��"
                + "��Ϊ��ʹԭʼ����ֻ���һ���ֽڣ���������ǩ��Ҳ�ܿ��ܲ�𼫴󡣴�������� ���� ˵��"
                + "Ҫ���һ�� hash �㷨�������Ƶ����ݲ�����ǩ��Ҳ������Ǹ�Ϊ���ѵ�������Ϊ����ǩ��ֵ�����ṩԭʼ"
                + "�����Ƿ���ȵ���Ϣ�⣬����1���ܶ����ṩ����ȵ� ԭʼ��������2���ݵĲ���̶ȵ���Ϣ��";
        SimHash hash3 = new SimHash(s, 64);
        System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitCount());
        hash1.subByDistance(hash3, 3);


        System.out.println("============================");


        int dis = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
        System.out.println("���Ƚ�"+hash1.hammingDistance(hash2) + " " + dis);
        // ���ݸ볲ԭ��Ҳ�ɳ���ԭ���������ѧ�����������ǩ���ĺ��������� 3 ���ڣ����Ǳ���һ��ǩ��subByDistance()��ȫ��ͬ��
        int dis2 = hash1.getDistance(hash2.strSimHash, hash3.strSimHash);
        System.out.println(hash2.hammingDistance(hash3) + " " + dis2);
        System.out.println(hash3.getStrSimHash());
    }
}