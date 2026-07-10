# Conceitos

Um **QR Code é um tipo de código de barras bidimensional (2D)**. Assim como um código de barras tradicional (1D), ele **codifica dados**. A diferença é que o QR Code armazena informações em duas dimensões (horizontal e vertical), o que permite guardar muito mais dados.

Quanto ao "em texto":

* **Nem todo QR Code representa texto**, mas **todo QR Code representa uma sequência de dados (bytes)**.
* Muitas aplicações armazenam texto porque é o caso mais comum: URLs, PIX Copia e Cola, JSON, identificadores, etc.
* Porém, ele também pode armazenar dados binários arbitrários (por exemplo, um pequeno arquivo ou informações compactadas).

Por exemplo:

| Conteúdo original    | O `getText()` do ZXing retorna |
| -------------------- | ------------------------------ |
| `https://openai.com` | `"https://openai.com"`         |
| `123456789`          | `"123456789"`                  |
| JSON                 | `{"id":1,"nome":"João"}`       |
| PIX Copia e Cola     | `00020126...`                  |
| vCard                | `BEGIN:VCARD...END:VCARD`      |

É por isso que, na prática, a API da ZXing expõe:

```java
Result result = new MultiFormatReader().decode(bitmap);

String texto = result.getText();
```

Na grande maioria dos casos, o conteúdo do QR Code é interpretado como uma `String`.

### Uma analogia

Pense no QR Code como um pendrive muito pequeno:

* O **QR Code** é o meio de armazenamento.
* Os **bytes** são os dados gravados.
* Esses bytes podem representar:

  * texto;
  * um número;
  * um JSON;
  * um link;
  * um vCard;
  * ou qualquer outro formato de dados.

Então, dizer que "todo QR Code é um código de barras em texto" não é tecnicamente preciso. O mais correto é dizer:

> **Todo QR Code é um código de barras 2D que codifica uma sequência de dados (bytes). Em muitas aplicações, esses dados representam texto, por isso bibliotecas como a ZXing normalmente retornam uma `String` ao decodificar.**
