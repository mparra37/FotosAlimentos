package mx.itson.fotosalimentos;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AgregarFotoActivity extends AppCompatActivity {

    private final int CODIGO_CAMARA = 123;
    private final int CODIGO_SELECCION = 234;
    private final int CODIGO_GUARDAR = 345;
    Button  btn_hora;
    String fecha_str, nombre_archivo;
    Uri img_uri;
    ImageView iv_foto;
    File imagen_file;
    EditText et_desc;
    Bitmap bitmap;
    private boolean tomo_foto = false;
    private boolean subio_foto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_foto);

        iv_foto = findViewById(R.id.iv_foto);
        btn_hora = findViewById(R.id.btn_hora);
        et_desc = findViewById(R.id.et_desc);

        Date fecha = new Date();
        estableceFecha(fecha);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            fecha_str = bundle.getString("fecha");

        }

    }

    public void permisos_guardar(View v){

            //Verifica los permisos
            //A partir de la versión de Android de Marshmallow
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //no tiene los permisos
                    String[] permisos = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    //muestra ventana para pedir los permisos al usuario
                    requestPermissions(permisos, CODIGO_GUARDAR);
                }else{
                    //tiene los permisos
                    guardar();
                }
            }else{
                //No es necesario pedir los permisos directamente
                guardar();
            }


    }

    public void cancelar(View v){
        if (tomo_foto){
            String nombreImg = nombre_archivo + ".jpg";
            imagen_file = new File(ubicacionCarpeta(), nombreImg);
            if (imagen_file.exists()){
                imagen_file.delete();
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
        if (tomo_foto){
            String nombreImg = nombre_archivo + ".jpg";
            imagen_file = new File(ubicacionCarpeta(), nombreImg);
            if (imagen_file.exists()){
                imagen_file.delete();
            }
        }
        finish();
    }
    private void guardar(){

        if(tomo_foto || subio_foto){
            String desc = et_desc.getText().toString();



            if (subio_foto && bitmap!= null){
                Toast.makeText(this,"guardando...espere un poco", Toast.LENGTH_LONG).show();
                try {
                    File imagen = new File(ubicacionCarpeta(), nombre_archivo + ".jpg");
                    FileOutputStream out = new FileOutputStream(imagen);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                    Toast.makeText(this,"Se guardó la imagen", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this,"No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            if (!desc.isEmpty()) {
                try{
                    File archivo = new File(ubicacionCarpeta(), nombre_archivo+".txt");
                    FileOutputStream fos = new FileOutputStream(archivo);
                    fos.write(desc.getBytes());
                    fos.close();
                    Toast.makeText(this,"Se guardó el archivo", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(this,"Error al guardar la descripción", Toast.LENGTH_SHORT).show();
                }

            }

            finish();

        }else{
            Toast.makeText(this,"Agregar una fotografía", Toast.LENGTH_SHORT).show();
        }


    }

    private void estableceFecha(Date fecha){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        fecha_str = formatoFecha.format(fecha);

        SimpleDateFormat formatoNombre = new SimpleDateFormat("HH-mm-ss-SSS");
        nombre_archivo = formatoNombre.format(fecha);

        //Establece la hora en el botón
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        String hora = formatoHora.format(fecha);
        btn_hora.setText(hora);
    }

    public void permisos_tomar_foto(View v){
        //Verifica los permisos
        //A partir de la versión de Android de Marshmallow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //no tiene los permisos
                String[] permisos = {android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

                //muestra ventana para pedir los permisos al usuario
                requestPermissions(permisos, CODIGO_CAMARA);
            }else{
                //tiene los permisos
                tomar_foto();
            }
        }else{
            //No es necesario pedir los permisos directamente
            tomar_foto();
        }
    }

    private void tomar_foto(){
        String nombreImg = nombre_archivo + ".jpg";
        imagen_file = new File(ubicacionCarpeta(), nombreImg);
        if (imagen_file.exists()){
            imagen_file.delete();
        }

        img_uri = Uri.fromFile(imagen_file);

        Intent camara_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camara_intent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri);
        startActivityForResult(camara_intent, CODIGO_CAMARA);
    }

    public void permisos_subir_foto(View v){
        //Verifica los permisos
        //A partir de la versión de Android de Marshmallow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //no tiene los permisos
                String[] permisos = {android.Manifest.permission.READ_EXTERNAL_STORAGE};

                //muestra ventana para pedir los permisos al usuario
                requestPermissions(permisos, CODIGO_SELECCION);
            }else{
                //tiene los permisos
                subir_foto();
            }
        }else{
            //No es necesario pedir los permisos directamente
            subir_foto();
        }

    }

    private void subir_foto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODIGO_SELECCION);
    }

    private String ubicacionCarpeta(){
        File carpeta = new File(Environment.getExternalStorageDirectory(),"PotroSaludable");
        if (!carpeta.exists()){
            carpeta.mkdir();
        }
        File dia = new File(carpeta, fecha_str);
        if(!dia.exists()){
            dia.mkdir();
        }
        return dia.getAbsolutePath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

         switch (requestCode){
             case CODIGO_CAMARA: if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                         grantResults[1] == PackageManager.PERMISSION_GRANTED ){
                     tomar_foto();
                 }else{
                     Toast.makeText(this,"Permisos denegados", Toast.LENGTH_SHORT).show();
                 };
                break;
             case CODIGO_SELECCION:if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                     subir_foto();
                 }else{
                     Toast.makeText(this,"Permisos denegados", Toast.LENGTH_SHORT).show();
                 };
                break;
             case CODIGO_GUARDAR:if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                 guardar();
             }else{
                 Toast.makeText(this,"Permisos denegados", Toast.LENGTH_SHORT).show();
             };
             break;
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CODIGO_CAMARA: if(resultCode == Activity.RESULT_OK){
                //iv_foto.setImageURI(img_uri);
                verificaRotacion(null);
                tomo_foto = true;
                subio_foto = false;
            }

            case CODIGO_SELECCION: if(resultCode == Activity.RESULT_OK && data != null){
//                val selectedImage = data.data
//                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//                val cursor = contentResolver.query(selectedImage, filePathColumn,null,null,null)
//                cursor.moveToFirst()
//
//                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//                val picturePath = cursor.getString(columnIndex)
//                cursor.close()
//                iv_comida.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                 img_uri = data.getData();
                 String[] ubi = {MediaStore.Images.Media.DATA};
                 Cursor cursor = getContentResolver().query(img_uri,ubi, null,null,null);
                 cursor.moveToFirst();

                 int idColumn = cursor.getColumnIndex(ubi[0]);
                 String path = cursor.getString(idColumn);
                 cursor.close();

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(path,bmOptions);
                 verificaRotacion(bitmap);
                // iv_foto.setImageBitmap(bitmap);

                subio_foto = true;
                tomo_foto = false;
            }
        }
    }


    //Verifica que no rote la imagen
    public void verificaRotacion(Bitmap bitmap){
        if (bitmap==null){
            bitmap = creaBitmap();
        }

        try{
            ExifInterface ei = new ExifInterface(img_uri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;

            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }

            //iv_foto.setImageURI(img_uri);
            iv_foto.setImageBitmap(rotatedBitmap);

        }catch (Exception e){

        }
    }

    private Bitmap creaBitmap(){

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        Bitmap bitmap = BitmapFactory.decodeFile(imagen_file.getAbsolutePath(),bmOptions);

       // bitmap = Bitmap.createScaledBitmap(bitmap,iv_foto.getWidth(),iv_foto.getHeight(),true);
        //iv_foto.setImageBitmap(bitmap);
        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
