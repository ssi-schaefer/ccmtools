

all: pdf_with_bibtex

pdf:
	pdflatex CcmtoolsManual

pdf_with_bibtex:
	pdflatex CcmtoolsManual
	bibtex CcmtoolsManual
	pdflatex CcmtoolsManual
	pdflatex CcmtoolsManual
	
clean:
	rm -rf *.aux *.bbl *.blg *.log *.out *.toc
	