conda create -n spylon python=3.8
conda activate spylon 
conda install -c conda-forge spylon-kernel
python -m spylon_kernel install
conda deactivate
