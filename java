function doPost(e) {
  try {
    const data = JSON.parse(e.postData.contents);
    
    // Enviar correo a tu Gmail
    const subject = `Nuevo turno - ${data.name}`;
    const body = `
NUEVA RESERVA DE TURNO

Servicio: ${data.service}
Precio: $${data.price}
Fecha: ${data.date}
Hora: ${data.time}

CLIENTE:
Nombre: ${data.name}
Teléfono: ${data.phone}
Email: ${data.email}

Reserva realizada: ${new Date().toLocaleString()}
    `;
    
    GmailApp.sendEmail(data.toEmail, subject, body);
    
    // También podrías guardar en Google Sheets
    const sheet = SpreadsheetApp.openById('TU_ID_DE_SHEET').getActiveSheet();
    sheet.appendRow([
      new Date(),
      data.name,
      data.phone,
      data.email,
      data.service,
      data.price,
      data.date,
      data.time
    ]);
    
    return ContentService.createTextOutput(JSON.stringify({success: true}))
      .setMimeType(ContentService.MimeType.JSON);
      
  } catch (error) {
    return ContentService.createTextOutput(JSON.stringify({error: error.toString()}))
      .setMimeType(ContentService.MimeType.JSON);
  }
}