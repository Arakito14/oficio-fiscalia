package com.manzanitacreations.proyectofiscalia;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.*;

public class Fiscalia {
    private HashMap<String,Fiscal> fiscales;
    private HashMap<String,Causa> causas;
    private String contraseña;

    public Fiscalia() {
        fiscales=new HashMap<>();
        causas=new HashMap<>();
        contraseña="admin";
    }
    
  
  /*Método para mostrar todos los fiscales del Hashtable fiscales*/
 public void mostrarFiscales(){
       for (Map.Entry<String, Fiscal> entry : fiscales.entrySet()) {
             Fiscal aux=entry.getValue();
             aux.imprimirFiscal();//Muestra los fiscales por pantalla
             System.out.println("Causas:");
            mostrarCausas(aux.getCausasActuales());//muestra las causas de cada fiscal por pantalla
            System.out.println("----------------------------------------------------------");
       }
 }
 
  /*Método para mostrar todas las causas de la Hashtable causas*/
 public  void mostrarCausas(){
     int tamaño=causas.size();
     if(tamaño!=0){
         for (Map.Entry<String, Causa> entry : causas.entrySet()) {
                Causa aux=entry.getValue();
               System.out.println("Codigo Causa:"+ aux.getCodigo());
               System.out.println("Rut Encargado:"+ aux.getEncargado().getRut());
               System.out.println("Estado:"+ aux.getEstado());
               System.out.println("Tipo de Caso:"+ aux.getTipoCaso());
               System.out.println("Distrito:"+ aux.getDistrito());
               System.out.println("----------------------------------------------------------");
          }
     } 
 }
 
 /*Método para mostrar todas las causas que tiene un fiscal*/
 public  void mostrarCausas(HashMap<String,Causa>causas){
     int tamaño=causas.size();
     if(tamaño!=0){
         for (Map.Entry<String, Causa> entry : causas.entrySet()) {
                Causa aux=entry.getValue();
               System.out.println("Codigo Causa:"+ aux.getCodigo());
               System.out.println("Rut Encargado:"+ aux.getEncargado().getRut());
               System.out.println("Estado:"+ aux.getEstado());
               System.out.println("Tipo de Caso:"+ aux.getTipoCaso());
               System.out.println("Distrito:"+ aux.getDistrito());
               System.out.println("----------------------------------------------------------");
          }
     } 
 }
 
  public  void escribirCausas(HashMap<String,Causa>causas, File f){
      try {
        FileWriter writer = new FileWriter(f);
        int tamaño=causas.size();
        if(tamaño!=0){
           for (Map.Entry<String, Causa> entry : causas.entrySet()) {
                 Causa aux=entry.getValue();
                 writer.write("Codigo Causa:"+ aux.getCodigo());
                 writer.write("Rut Encargado:"+ aux.getEncargado().getRut());
                 writer.write("Estado:"+ aux.getEstado());
                 writer.write("Tipo de Caso:"+ aux.getTipoCaso());
                 writer.write("Distrito:"+ aux.getDistrito());
                 writer.write("----------------------------------------------------------");
                 aux.escribirProcedimientos(f);
            }
       }
      }
      catch (Exception e) {
         System.err.println(e);
     }
 }
 
 /*Método para buscar un fiscal y mostrarlo por pantalla*/
 /*Retorna el fiscal si es que existe y null en caso contrario*/
 public Fiscal buscarFiscal(){
     Scanner leer= new Scanner(System.in);
     Pattern patron = Pattern.compile("[0-9]{8}-[0-9]{1}");
     System.out.println("Ingrese el rut del fiscal sin puntos y con guion");
     String rut= leer.nextLine();
     Matcher mat = patron.matcher(rut);
     while(!mat.matches()){
          System.out.println("El formato es incorrecto. Por favor intente de nuevo");
          rut=leer.nextLine();
          mat=patron.matcher(rut);
     }
     Fiscal buscado= fiscales.get(rut);
      if(buscado==null){
           System.out.println("El fiscal buscado no existe");
           return null;
       }else{
           buscado.imprimirFiscal();
           System.out.println("Causas:");
           mostrarCausas(buscado.getCausasActuales());
      }
      return buscado;
 }
 
 /*Método para buscar una causa y mostrarla por pantalla*/
 /*Retorna la causa si es que existe y null en caso contrario*/
 public Causa buscarCausa(){
     Scanner leer= new Scanner(System.in);
          System.out.println("Ingrese el codigo de la causa");
          Pattern patron = Pattern.compile("[0-9]{6}");
          String codigo=leer.nextLine();
          Matcher mat = patron.matcher(codigo);
           while(!mat.matches()){
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     codigo=leer.nextLine();
                     mat=patron.matcher(codigo);
            }
     Causa buscar= causas.get(codigo);
      if(buscar!=null){
                   buscar.imprimirCausa();
                   buscar.mostrarProcedimientos();
                   System.out.println("----------------------------------------------------------");
                   return buscar;
      }else{
          System.out.println("La causa buscada no existe");
          return null;
      }
     
 }
 
 /*Método para buscar una de las causas de un fiscal y mostrarla por pantalla*/
 /*Recibe como parámetro el mapa de causas dentro del fiscal*/
 public void buscarCausa(HashMap<String,Causa> causas){
          Scanner leer= new Scanner(System.in);
          System.out.println("Ingrese el codigo de la causa");
          Pattern patron = Pattern.compile("[0-9]{6}");
          String codigo=leer.nextLine();
          Matcher mat = patron.matcher(codigo);
           while(!mat.matches()){
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     codigo=leer.nextLine();
                     mat=patron.matcher(codigo);
            }
     Causa buscar= causas.get(codigo);
      if(buscar!=null){
                   buscar.imprimirCausa();
                   buscar.mostrarProcedimientos();
                   System.out.println("----------------------------------------------------------");
      }else{
          System.out.println("La causa buscada no existe");
      }
     
 }
 
  /*Método para leer un fiscal e ingresarlo a la Hashtable fiscales*/
 public void nuevoFiscal(){
     Scanner leer= new Scanner(System.in);
      Pattern patron = Pattern.compile("[0-9]{8}-[0-9]{1}");
     System.out.println("Ingrese el rut del fiscal sin puntos y con guion");
     String rut= leer.nextLine();
     Matcher mat = patron.matcher(rut);
     while(!mat.matches()){
          System.out.println("El formato es incorrecto. Por favor intente de nuevo");
          rut=leer.nextLine();
          mat=patron.matcher(rut);
     }
     boolean existe= fiscales.containsKey(rut);
     if(existe){
         System.out.println("Ya existe un fiscal con ese rut");
     }else{
         System.out.println("Ingrese el nombre del fiscal");
         String nombre=leer.nextLine();
         System.out.println("Ingrese la especialidad del fiscal");
         String especialidad=leer.nextLine();
         System.out.println("Ingrese el distrito");
          String dist=leer.nextLine();
         int distrito=0;
         boolean esNum;
         do{
             esNum=true;
             try {
                     distrito=Integer.parseInt(dist);
                     if(distrito<1|| distrito>28){
                         esNum=false;
                         System.out.println("El numero ingresado debe ser entre 1 y 28. Por favor intente de nuevo");
                         dist=leer.nextLine();
                     }
             } catch (NumberFormatException nfe){
                     esNum=false;
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     dist=leer.nextLine();
              }
         }while(!esNum);
     
         Fiscal nuevo= new Fiscal(nombre,rut,especialidad,distrito);
         fiscales.put(rut,nuevo);
     }
     
 }
 
 /*Método para leer una causa e ingresarla a la HashTable causas*/
 public void nuevaCausa(){
      Scanner leer= new Scanner(System.in);
          System.out.println("Ingrese el codigo de la causa a asignar");
          Pattern patron = Pattern.compile("[0-9]{6}");
          String codigo=leer.nextLine();
          Matcher mat = patron.matcher(codigo);
           while(!mat.matches()){
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     codigo=leer.nextLine();
                     mat=patron.matcher(codigo);
            }
     boolean existe= causas.containsKey(codigo);
     if(existe){
         System.out.println("Ya existe este caso");
     }else{
         System.out.println("Ingrese el nombre del estado del caso");
         String estado=leer.nextLine();
         System.out.println("Ingrese el tipo de caso");
         String tipoCaso=leer.nextLine();
         System.out.println("Ingrese el distrito");
         String dist=leer.nextLine();
         int distrito=0;
         boolean esNum;
         do{
             esNum=true;
             try {
                     distrito=Integer.parseInt(dist);
                     if(distrito<1|| distrito>28){
                         esNum=false;
                         System.out.println("El numero ingresado debe ser entre 1 y 28. Por favor intente de nuevo");
                         dist=leer.nextLine();
                     }
             } catch (NumberFormatException nfe){
                     esNum=false;
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     dist=leer.nextLine();
              }
         }while(!esNum);
         Causa nueva= new Causa(codigo,estado,tipoCaso,distrito);
         causas.put(codigo,nueva);
     }
     
 }
 
 /*Método para buscar la causa a la que se le agregará un nuevo procedimiento*/
  public void nuevoProcedimiento(){
      Scanner leer= new Scanner(System.in);
          System.out.println("Ingrese el codigo de la causa");
          Pattern patron = Pattern.compile("[0-9]{6}");
          String codigo=leer.nextLine();
          Matcher mat = patron.matcher(codigo);
           while(!mat.matches()){
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     codigo=leer.nextLine();
                     mat=patron.matcher(codigo);
           }
      Causa buscar= causas.get(codigo);
      if(buscar!=null){
                   buscar.imprimirCausa();
                   buscar.mostrarProcedimientos();
                   System.out.println("----------------------------------------------------------");
                   nuevoProcedimiento(buscar);             
      }else{
          System.out.println("La causa buscada no existe");
      }
     }  
  
  /*Método que agrega el nuevo procedimiento a la causa*/
   public void nuevoProcedimiento(Causa buscar){
       Scanner leer= new Scanner(System.in);
       System.out.println("Ingrese el nombre del nuevo procedimiento");
       String nombreProc=leer.nextLine();
       Procedimiento nuevo=new Procedimiento();
       nuevo.setNombreProc(nombreProc);
       int opcion=0;
       do{
           System.out.println("Ingrese el nombre del participante");
           String nombre=leer.nextLine();
           System.out.println("Ingrese el rol del participante (policia, abogado, testigo,etc...)");
           String rol=leer.nextLine();
           nuevo.ingresarParticipante(nombre, rol);
           System.out.println("¿Desea ingresar más participantes?");
           System.out.println("1-SI/0-NO"); 
           opcion=Integer.parseInt(leer.nextLine());
      }while(opcion!=0);
      System.out.println("Ingrese el resultado del nuevo procedimiento");
      String result=leer.nextLine();
      nuevo.setResultado(result);
      buscar.getPeritajes().addLast(nuevo);
   }
 
  /*Método para asignar un fiscal a una causa*/
   public void asignarFiscal(){
          Scanner leer= new Scanner(System.in);
          System.out.println("Ingrese el codigo de la causa a asignar");
          Pattern patron = Pattern.compile("[0-9]{6}");
          String codigo=leer.nextLine();
          Matcher mat = patron.matcher(codigo);
           while(!mat.matches()){
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     codigo=leer.nextLine();
                     mat=patron.matcher(codigo);
            }
          Causa asignada=causas.get(codigo);
          if(asignada!=null){
                System.out.println(codigo);
                asignada.asignarFiscal(fiscales,asignada);
          }else{
                 System.out.println("La causa requerida no existe");
          }
   }
   
 /*Método para buscar un fiscal y modificar su distrito*/
 public void modificarDistrito(){
     Scanner leer= new Scanner(System.in);
     Pattern patron = Pattern.compile("[0-9]{8}-[0-9]{1}");
     System.out.println("Ingrese el rut del fiscal sin puntos y con guion");
     String rut= leer.nextLine();
     Matcher mat = patron.matcher(rut);
     while(!mat.matches()){
          System.out.println("El formato es incorrecto. Por favor intente de nuevo");
          rut=leer.nextLine();
          mat=patron.matcher(rut);
     }
     Fiscal buscado= fiscales.get(rut);
     if(buscado==null){
           System.out.println("No se ha encontrado al fiscal buscado");
     }else{
           buscado.imprimirFiscal();
           System.out.println("Ingrese el nuevo distrito");
           int dist= Integer.parseInt(leer.nextLine());
           while(dist<0 || dist>28){
                  System.out.println("El valor ingresado no es valido. Por favor intente de nuevo");
                  dist= Integer.parseInt(leer.nextLine());
           }
           buscado.setDistrito(dist);
      }
 }
 
 /*Método para cambiar el fiscal de una causa*/
 public void cambiarFiscal(){
          Scanner leer= new Scanner(System.in);
          System.out.println("Ingrese el codigo de la causa requerida");
          Pattern patron = Pattern.compile("[0-9]{6}");
          String codigo=leer.nextLine();
          Matcher mat = patron.matcher(codigo);
           while(!mat.matches()){
                     System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                     codigo=leer.nextLine();
                     mat=patron.matcher(codigo);
            }
          Causa asignada=causas.get(codigo);
          if(asignada!=null){
                patron = Pattern.compile("[0-9]{8}-[0-9]{1}");
                System.out.println("Ingrese el rut del fiscal sin puntos y con guion");
                String rut= leer.nextLine();
                 mat = patron.matcher(rut);
                 while(!mat.matches()){
                         System.out.println("El formato es incorrecto. Por favor intente de nuevo");
                         rut=leer.nextLine();
                         mat=patron.matcher(rut);
                }
                Fiscal nuevo=fiscales.get(rut);
                asignada.reemplazarFiscal(nuevo, fiscales, asignada);
          }else{
                 System.out.println("La causa requerida no existe");
          }        
 }
 /*Modifica un procedimiento de una causa*/
 public void modificarProcedimiento(){
        Causa buscar=buscarCausa();
        buscar.modificarProcedimiento();
 }
 /*Elimina un fiscal de los mapas*/
 public void eliminarFiscal(){
        Fiscal eliminado=buscarFiscal();
        if(eliminado!=null){
            eliminarFiscal(eliminado.getCausasActuales());
            fiscales.remove(eliminado.getRut());
            System.out.println("El fiscal ha sido eliminado con exito");
        }else{
            System.out.println("No hay nada que eliminar");
        }
 }
 /*Elimina el fiscal de todas las causas que tenía a su cargo*/
 public void eliminarFiscal(HashMap<String,Causa> causas){
     for (Map.Entry<String,Causa> entry : causas.entrySet()) {
           Causa aux=entry.getValue();
           aux.setEncargado(new Fiscal());
     }
     causas.clear();
 }
 /*Elimina la causa de todos los mapas*/
 public void eliminarCausa(){
      Causa eliminada=buscarCausa();
      Fiscal encargado= fiscales.get(eliminada.getEncargado().getRut());
       if(encargado!=null){
           encargado.getCausasActuales().remove(eliminada.getCodigo());
           eliminada.getPeritajes().clear();
       }
      causas.remove(eliminada.getCodigo());
      System.out.println("La causa ha sido eliminada con éxito");      
 }
 /*Busca la causa a la que se le va a eliminar un procedimiento*/
 public void eliminarProcedimiento(){
     Causa eliminada=buscarCausa();
     eliminada.eliminarProcedimiento();
 }

/**------------------------------------------Getter y Setter------------------------------------------------*/
    public HashMap<String, Fiscal> getFiscales() {
        return fiscales;
    }

    public void setFiscales(HashMap<String, Fiscal> fiscales) {
        this.fiscales = fiscales;
    }

    public HashMap<String, Causa> getCausas() {
        return causas;
    }

    public void setCausas(HashMap<String, Causa> causas) {
        this.causas = causas;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
