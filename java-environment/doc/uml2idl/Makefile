DOCUMENT_NAME=uml2idl_ger

all: dvi 

dvi: $(DOCUMENT_NAME).dvi
$(DOCUMENT_NAME).dvi: $(DOCUMENT_NAME).tex
	latex $(DOCUMENT_NAME).tex

bi: $(DOCUMENT_NAME).tex
	bibtex $(DOCUMENT_NAME)
	latex $(DOCUMENT_NAME).tex
	latex $(DOCUMENT_NAME).tex

pdf: $(DOCUMENT_NAME).pdf
$(DOCUMENT_NAME).pdf: $(DOCUMENT_NAME).dvi
	dvipdf -sPAPERSIZE=a4 $(DOCUMENT_NAME)

ps: $(DOCUMENT_NAME).ps
$(DOCUMENT_NAME).ps: $(DOCUMENT_NAME).dvi
	dvips -f $(DOCUMENT_NAME).dvi > $(DOCUMENT_NAME).ps

CLEANFILES=\
*.aux *.toc *.log \
$(DOCUMENT_NAME).dvi $(DOCUMENT_NAME).ps  \
$(DOCUMENT_NAME).bbl $(DOCUMENT_NAME).blg \
$(DOCUMENT_NAME).lof $(DOCUMENT_NAME).lot

clean:
	@-rm $(CLEANFILES)>/dev/null 2>&1
 









