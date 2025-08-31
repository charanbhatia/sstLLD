public class Player {
    private Frame last;
    void play(byte[] fileBytes){
        // decode
        Frame f = new Frame(fileBytes); // pretend decoding
        last = f;
        // draw UI
        System.out.println("\u25B6 Playing " + fileBytes.length + " bytes");
        // cache
        System.out.println("Cached last frame? " + (last!=null));
    }
}