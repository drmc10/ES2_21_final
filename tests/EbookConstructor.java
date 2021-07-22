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

    @Test
    public void CreateEbookTitleNull_Object() {
        Assertions.assertThrows(NullParameterException.class, () -> new Ebook(null, "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void CreateEbookPublisherNull_Object() {
        Assertions.assertThrows(NullParameterException.class, () -> new Ebook("Title", null, 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void CreateEbookHashNull_Object() {
        Assertions.assertThrows(NullParameterException.class, () -> new Ebook("Title", "Publisher", 5,
                null, 1000, ".pdf"));
    }

    @Test
    public void CreateEbookExtensionNull_Object() {
        Assertions.assertThrows(NullParameterException.class, () -> new Ebook("Title", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, null));
    }

    @Test
    public void CreateEbookValorInferiorTitle() {
        Assertions.assertDoesNotThrow(() -> new Ebook("anjos", "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void CreateEbookValorSuperiorTitle() {
        Assertions.assertDoesNotThrow(() -> new Ebook("anjos_e_demonios_anjos_e_demonios_anjos_e_demonios",
                "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void CreateEbookValorInferiorPublisher() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title", "porto", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void CreateEbookValorSuperiorPublisher() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title",
                "porto_editora_porto_editora_porto_editora_porto_ed", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookTitleAbove50() {
        Assertions.assertThrows(TooManyCharsException.class, () -> new Ebook("anjos_e_demonios_anjos_e_demonios_anjos_e_demonioss",
                "Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookTitleBelow5() {
        Assertions.assertThrows(TooFewCharsException.class, () -> new Ebook("anjo","Publisher", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookPublisherAbove50() {
        Assertions.assertThrows(TooManyCharsException.class, () -> new Ebook("anjos",
                "porto_editora_porto_editora_porto_editora_porto_edi", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookPublisherBelow5() {
        Assertions.assertThrows(TooFewCharsException.class, () -> new Ebook("anjos","port", 5,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookPagesAbove2000() {
        Assertions.assertThrows(IncorrectPageNumber.class, () -> new Ebook("Title","Publisher", 20001,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }

    @Test
    public void testEbookValorLimiteSuperiorPages() {
        Assertions.assertDoesNotThrow(() -> new Ebook("Title","Publisher", 2000,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 1000, ".pdf"));
    }
}
