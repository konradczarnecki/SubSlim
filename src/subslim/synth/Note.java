package subslim.synth;

/**
 * Created by konra on 21.02.2017.
 */
public class Note {

    public static final String[] representations = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H"};

    private int noteHeight; // C - 0, C# - 1, D - 2... H - 11
    private int octave;

    public Note(int noteHeight, int octave){

        this.noteHeight = noteHeight;
        this.octave = octave;
    }

    public Note(String noteCode){

        String notesOrder = "CcDdEFfGgAaH"; // A - A, A# - a, D# - d, F - F...

        noteHeight = notesOrder.indexOf(noteCode.substring(0,1));
        octave = Integer.parseInt(noteCode.substring(1,2));
    }

    public Note transpose(int change){

        int octaveChange = 0;
        int newOctave = octave;
        int newHeight = noteHeight;

        if(change >= 0 && Math.abs(change) >= 12) octaveChange = (int) Math.floor(change/12);
        else if (change < 0 && Math.abs(change) >= 12) octaveChange = (int) Math.ceil(change/12);

        change = change % 12;

        newOctave += octaveChange;

        newHeight += change;

        if(newHeight >= 12) {
            newHeight %= 12;
            newOctave++;
        }

        return new Note(newHeight, newOctave);
    }

    public int difference(Note otherNote){

        int noteHeightDifference = this.noteHeight - otherNote.noteHeight;
        int octaveDifference = this.octave - otherNote.octave;

        return octaveDifference*12+noteHeightDifference;
    }

    public boolean equals(Object obj){

        if(obj == null) return false;
        else if(!(obj instanceof Note)) return false;

        Note otherNote = (Note) obj;

        if(this.noteHeight == otherNote.noteHeight && this.octave == otherNote.octave) return true;
        else return false;
    }
}
