package br.com.martinello.componentesbasicos;

public interface Campo {

    public void limpar();

    public void setEnabled(boolean status);

    //public boolean eObrigatorio();

    public boolean eValido();

    public boolean eVazio();

    public String getDica();

    public String getDescricaoRotulo();

    public void setDescricaoRotulo(String descricaoRotulo);

    public boolean isObrigatorio();

    public void setObrigatorio(boolean obrigatorio);

    public Rotulo getComponenteRotulo();

    public void setComponenteRotulo(Rotulo rRotulo);
}
