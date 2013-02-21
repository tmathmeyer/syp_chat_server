package edu.wpi.tmathmeyer.chat.test;

import java.io.IOException;

import edu.wpi.tmathmeyer.chat.protocol.MessagePacket;

public class PseudoIO {
	BitQueue bq;
	
	public void addByte(byte b){
		if (bq==null){
			bq = new ByteQueue(b);
			return;
		}
		bq = bq.addByte(b);
	}
	
	public void addShort(short c){
		if (bq==null){
			bq = new ShortQueue(c);
			return;
		}
		bq = bq.addShort(c);
	}
	
	public void print(){
		if (bq!=null)bq.print(1);
	}
	
	public byte getByte(){
		byte res = bq.getByte();
		bq = bq.getQueue();
		return res;
	}
	
	public short getShort(){
		short res = bq.getShort();
		bq = bq.getQueue();
		return res;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException{
		
		MessagePacket mp = new MessagePacket("herro", "000000", "000000", "noob");
		PseudoIO pi = new PseudoIO();
		PseudoOutputStream pos = new PseudoOutputStream(pi);
		PseudoInputStream pis = new PseudoInputStream(pi);
		
		//mp.write(pos);
		
		pi.print();
		
		//MessagePacket newPacket = new MessagePacket(pis);
		
		System.out.println("lol");
		
	}
}




interface BitQueue{
	public int getType(); // 1 = char, 2 = byte
	public byte getByte();
	public short getShort();
	public BitQueue addShort(short c);
	public BitQueue addByte(byte b);
	public BitQueue getQueue();
	public void print(int depth);
}

class ShortQueue implements BitQueue{
	short c=1000;
	BitQueue q;
	public ShortQueue(short c){
		this.c = c;
	}
	@Override
	public int getType() {
		return 1;
	}
	@Override
	public byte getByte() {
		//print(0);
		System.exit(0);
		return 0x00;
	}
	@Override
	public short getShort() {
		return this.c;
	}
	@Override
	public BitQueue addShort(short c) {
		if (this.c == 1000)this.c = c;
		else if (this.q == null) q = new ShortQueue(c);
		else this.q = this.q.addShort(c);
		return this;
	}
	@Override
	public BitQueue addByte(byte b) {
		if (this.c == '\u0000')return new ByteQueue(b);
		else if (this.q == null) q = new ByteQueue(b);
		else this.q = this.q.addByte(b);
		return this;
	}
	@Override
	public BitQueue getQueue() {
		return this.q;
	}
	@Override
	public void print(int depth) {
		System.out.println(c);
		if (q!=null)q.print(depth+1);
		else System.out.println(depth);
	}
}
	
class ByteQueue implements BitQueue{
	byte c = 100;
	BitQueue q;
	public ByteQueue(byte c){
		this.c = c;
	}
	@Override
	public int getType() {
		return 2;
	}
	@Override
	public byte getByte() {
		return c;
	}
	@Override
	public short getShort() {
		System.out.println("shitson");
		System.exit(0);
		return 0;
	}
	@Override
	public BitQueue addShort(short c) {
		if (this.c == 100)return new ShortQueue(c);
		else if (this.q == null) q = new ShortQueue(c);
		else this.q = this.q.addShort(c);
		return this;
	}
	@Override
	public BitQueue addByte(byte b) {
		if (this.c == 0)this.c = b;
		else if (this.q == null) q = new ByteQueue(b);
		else this.q = this.q.addByte(c);
		return this;
	}
	@Override
	public BitQueue getQueue() {
		return this.q;
	}
	@Override
	public void print(int depth) {
		System.out.println(c);
		if (q!=null)q.print(depth+1);
	}
}
