package anaydis.sandbox.bitManipulation;

public class BitManipulation {

    public boolean bitAt(String s, int nth){
        int pos = nth / 8;  // Calculate the position of the char that contains the bit
        int shift = 7 - (nth % 8);  // Calculate how many positions to shift to isolate the bit
        return ((s.charAt(pos) >> shift) & 1) != 0;  // Extract the bit and return as boolean
    }

    public byte nibbleAt(String s, int nth){
        int pos = nth / 2; // Calculate the pos of the char that contains the nibble
        int shift = (1 - (nth % 2)) * 4;// Calculate how many positions to shift to get the crumble
                                        // we want in the last 2 bits of a byte
        return (byte) ((s.charAt(pos) >> shift) & 0x0F);//0c0F = 0000 1111 (turn off the first 4 bits)
    }

    public byte crumbleAt(String s, int nth){
        int pos = nth / 4; // Calculate the pos of the char that contains the crumble
        int shift = (3 - (nth % 4)) * 2;// Calculate how many positions to shift to get the crumble
                                        // we want in the last 2 bits of a byte
        return (byte) ((s.charAt(pos) >> shift) & 0x03); //0x03 = 00 00 00 11 (turn off the first 6 bits)
    }

}
