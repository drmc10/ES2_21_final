import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EbookConstructor {
    @Test
    public void testEbookMissingTitle() {
        Assertions.assertThrows(MissingTitleException.class, () -> new Ebook("", "Publisher", 50,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookMissingPublisher() {
        Assertions.assertThrows(MissingPublisherException.class, () ->  new Ebook("Title", "", 50,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookNegativePages() {
        Assertions.assertThrows(IncorrectPageNumber.class, () ->new Ebook("Title", "Publisher", -50,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookZeroPages() {
        Assertions.assertThrows(IncorrectPageNumber.class, () -> new Ebook("Title", "Publisher", 0,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookHashAbove64() {
        Assertions.assertThrows(InvalidHashException.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D381", 1000, ".pdf"));
    }

    @Test
    public void testEbookHashBelow64() {
        Assertions.assertThrows(InvalidHashException.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D3", 1000, ".pdf"));
    }

    @Test
    public void testEbookHashEmpty() {
        Assertions.assertThrows(InvalidHashException.class, () -> new Ebook("Title", "Publisher", 5,
                "", 1000, ".pdf"));
    }

    @Test
    public void testEbookHashOk() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookWrongExtension() {
        Assertions.assertThrows(InvalidFileExtension.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000,
                ".exe"));
    }

    @Test
    public void testEbookEmptyExtension() {
        Assertions.assertThrows(InvalidFileExtension.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000,
                ""));
    }

    @Test
    public void testEbookOkPdf() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookOkEpub() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".epub"));
    }

    @Test
    public void testEbookAbove100000size() {
        Assertions.assertThrows(InvalidFileSizeException.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 100001,
                ".pdf"));
    }

    @Test
    public void testEbookNullSize() {
        Assertions.assertThrows(InvalidFileSizeException.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 0,
                ".pdf"));
    }

    @Test
    public void testEbookMaxSize() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 100000,
                ".pdf"));
    }

    @Test
    public void testEbookMinSize() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1,
                ".pdf"));
    }

    @Test
    public void testEbookNegativeSize() {
        Assertions.assertThrows(InvalidFileSizeException.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", -1,
                ".pdf"));
    }
}
